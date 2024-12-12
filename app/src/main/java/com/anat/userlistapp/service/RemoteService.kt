package com.anat.userlistapp.service

import com.anat.userlistapp.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path



interface RemoteService {

    @GET("Users/page/{page}")
    suspend fun getUsers(
        @Path("page") page: Int
    ): List<UserResponse>

    @DELETE("users/{email}")
    suspend fun deleteUser(
        @Path("uuid") uuid: String
    ): Response<Void>

    @PATCH("Users/{uuid}")
    suspend fun favoriteUser(
        @Path("uuid") uuid: String,
        @Body body: Map<String, Boolean>
    ): Response<Void>
}

data class UserResponseList(
    val results: List<UserResponse>
)
