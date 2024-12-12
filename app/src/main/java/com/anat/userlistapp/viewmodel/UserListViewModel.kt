package com.anat.userlistapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anat.userlistapp.model.User
import com.anat.userlistapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isInitLoading = MutableStateFlow(true)
    val isInitLoading: StateFlow<Boolean> = _isInitLoading

    private val _retrying = MutableStateFlow(false)
    val retrying: StateFlow<Boolean> = _retrying


    private val _showUserDetailsPopup = MutableStateFlow(false)
    val showUserDetailsPopup: StateFlow<Boolean> = _showUserDetailsPopup

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser

    private var currentPage = 1

    init {
        viewModelScope.launch {
            loadUsers()
        }
    }

    fun loadUsers() {
        if (_isLoading.value || _retrying.value) return
        _retrying.value = true
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newUsers: List<User>? = userRepository.fetchUsers(currentPage)
                if (newUsers != null) {
                    _error.value = false
                    if (_users.value.map { it.email }.toSet() != newUsers.map { it.email }.toSet()) {
                        val filteredUsers = newUsers.filterNot { user -> _users.value.any { it.email == user.email } }
                        if (filteredUsers.isNotEmpty()) _users.value += filteredUsers
                        currentPage++
                    }
                } else  {
                    _error.value = true
                }
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _retrying.value = false
                _isLoading.value = false
                _isInitLoading.value = false
            }
        }
    }

    fun loadMoreUsers() {
        loadUsers()
    }


    fun refresh() {
        viewModelScope.launch {
            userRepository.deleteAllDaoUsers()
        }
        _users.value = emptyList()
        currentPage = 1
        loadUsers()
        _isInitLoading.value = true
    }

    fun showUserDetailsPopup(user: User) {
        _selectedUser.value = user
        _showUserDetailsPopup.value = true
    }

    fun closeUserDetailsPopup() {
        _showUserDetailsPopup.value = false
        _selectedUser.value = null
    }

    @SuppressLint("ServiceCast")
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            try {
                userRepository.deleteUserFromLocal(user)

                val isDeletedFromRemote = userRepository.deleteUserFromRemote(user)

                if (isDeletedFromRemote) {
                    _users.value = _users.value.filter { it.email != user.email }
                } else {
                    _error.value = true
                }
            } catch (e: Exception) {
                _error.value = true
            }
        }
    }

    fun favoriteUser(uuid: String, isFavorite: Boolean) {
        try {
            viewModelScope.launch {
                val response = userRepository.favoriteUser(uuid, isFavorite)
                if (response.isSuccessful) {
                    _users.update { users ->
                        users.map { user ->
                            if (user.uuid == uuid) {
                                user.copy(isFavorite = isFavorite)
                            } else {
                                user
                            }
                        }
                    }
                    _selectedUser.value!!.isFavorite = !_selectedUser.value!!.isFavorite
                }
            }
        } catch (e: Exception) {
            Log.e("UserListViewModel", "Error while starring user")
        }
    }
}
