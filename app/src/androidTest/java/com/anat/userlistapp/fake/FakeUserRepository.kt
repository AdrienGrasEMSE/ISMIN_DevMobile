package com.anat.userlistapp.fake

import com.anat.userlistapp.model.User
import com.anat.userlistapp.repository.UserRepository

class FakeUserRepository : UserRepository {

    private var shouldReturnError = false
    private var emptyUserList = false

    private var fakeUsersList = listOf(
        User(fullName = "John Doe", email = "john.doe@example.com", avatarUrl = "url1"),
        User(fullName = "Jane Smith", email = "jane.smith@example.com", avatarUrl = "url2")
    )


    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun setEmptyUserList(value: Boolean) {
        emptyUserList = value
    }

    fun setUsers(users: List<User>) {
        fakeUsersList = users
    }

    override suspend fun fetchUsers(currentPage: Int): List<User>? {
        if (shouldReturnError) throw Exception("Simulated network error")
        if (emptyUserList) return null
        return fakeUsersList
    }

    override suspend fun deleteAllUsers() {
        fakeUsersList = emptyList()
    }

    override suspend fun validateAndInsertUsers(users: List<User>): List<User> {
        return emptyList()
    }


}