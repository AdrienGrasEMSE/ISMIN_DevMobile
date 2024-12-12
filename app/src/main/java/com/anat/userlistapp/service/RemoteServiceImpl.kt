package com.anat.userlistapp.service

import com.anat.userlistapp.model.UserResponse
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteServiceImpl @Inject constructor(
    private val retrofit: Retrofit
) : RemoteService {
    private val api = retrofit.create(RemoteService::class.java)


    override suspend fun getUsers(page: Int) : List<UserResponse> {
        try {
            val response = api.getUsers(page)
            return response
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun deleteUser(uuid: String): Response<Void> {
        return try {
            api.deleteUser(uuid)
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, "Internal Error"))
        }
    }

    override suspend fun favoriteUser(uuid: String, body: Map<String, Boolean>): Response<Void> {
        return try {
            api.favoriteUser(uuid, body)
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, "Internal Error"))
        }
    }
}