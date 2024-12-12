package com.anat.userlistapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anat.userlistapp.model.User
import com.anat.userlistapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserMapViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            loadUsersForMap()
        }
    }

    /**
     * Charge les utilisateurs avec leurs coordonnées géographiques
     */
    suspend fun loadUsersForMap() {
        _users.value = userRepository.fecthDAOUsers()!!
    }

    /**
     * Recharge les utilisateurs en réinitialisant les données
     */

    fun favoriteUser(uuid: String, isFavorite: Boolean): () -> Unit {
        return {
            viewModelScope.launch {
                userRepository.favoriteUser(uuid, isFavorite)
            }
        }
    }

    fun deleteUser(user: User): () -> Unit {
        return {
            viewModelScope.launch {
                try {
                    userRepository.deleteUserFromLocal(user)

                    val isDeletedFromRemote = userRepository.deleteUserFromRemote(user)

                    if (isDeletedFromRemote) {
                        val updatedUsers = users.value.filter { it.email != user.email }
                        _users.value = updatedUsers
                    } else {
                        _error.value = true
                    }
                } catch (e: Exception) {
                    _error.value = true
                }
            }
        }
    }
}
