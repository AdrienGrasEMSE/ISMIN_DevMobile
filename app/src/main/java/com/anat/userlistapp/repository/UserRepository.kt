package com.anat.userlistapp.repository

import com.anat.userlistapp.model.User
import retrofit2.Response


interface UserRepository {
    suspend fun fetchUsers(currentPage : Int): List<User>?
    suspend fun fecthDAOUsers() : List<User>?
    suspend fun deleteUserFromLocal(user : User)
    suspend fun deleteUserFromRemote(user : User) : Boolean
    suspend fun deleteAllDaoUsers()
    suspend fun validateAndInsertUsers(users: List<User>): List<User>
    suspend fun favoriteUser(uuid: String, isFavorite : Boolean) : Response<Void>
 }
