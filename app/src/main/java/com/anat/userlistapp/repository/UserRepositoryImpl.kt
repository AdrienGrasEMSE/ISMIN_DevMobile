package com.anat.userlistapp.repository

import com.anat.userlistapp.model.User
import com.anat.userlistapp.model.mapper.toUser
import com.anat.userlistapp.model.mapper.UserMapper
import com.anat.userlistapp.model.mapper.toUserDB
import com.anat.userlistapp.service.RemoteService
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteService: RemoteService,
    private val userDao: UserDao,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun fetchUsers(currentPage: Int): List<User>? {
        try {
            val userResponses = remoteService.getUsers(currentPage)
            if (userResponses.isEmpty()) {
                val users = userDao.getAllUsers()
                return if (users.isNotEmpty()) {
                    users.map { it.toUser() }
                } else {
                    null
                }
            } else {
                val users = userResponses.map { userMapper.transformResponseToUser(it) }
                val validUsers = validateAndInsertUsers(users)
                return validUsers
            }
        } catch (e: Exception) {
            return fecthDAOUsers()
        }
    }


    override suspend fun deleteAllDaoUsers() {
        userDao.deleteAllUsers()
    }

    override suspend fun validateAndInsertUsers(users: List<User>): List<User> {
        val validUsers = users.filter { user ->
            if (user.email.isEmpty()) {
                false
            } else true
        }
        userDao.insertUsers(validUsers.map{it.toUserDB()})
        return validUsers
    }

    override suspend fun favoriteUser(uuid: String, isFavorite : Boolean) : Response<Void> {
        val body = mapOf("isFavorite" to isFavorite)
        val response = remoteService.favoriteUser(uuid,body)
        if (response.isSuccessful) {
            userDao.favoriteUser(uuid, isFavorite)
            return response
        }
        return response
    }

    override suspend fun fecthDAOUsers() : List<User>? {
        val users = userDao.getAllUsers()
        return if (users.isNotEmpty()) {
            users.map { it.toUser() }
        } else {
            null
        }
    }

    override suspend fun deleteUserFromLocal(user: User) {
        userDao.deleteUserByUuid(user.uuid)
    }

    override suspend fun deleteUserFromRemote(user: User): Boolean {
        return try {
            remoteService.deleteUser(user.uuid)
            true
        } catch (e: Exception) {
            false
        }
    }
}