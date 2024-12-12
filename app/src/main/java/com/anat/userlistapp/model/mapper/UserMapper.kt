package com.anat.userlistapp.model.mapper

import com.anat.userlistapp.model.User
import com.anat.userlistapp.model.UserDB
import com.anat.userlistapp.model.UserResponse
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun transformResponseToUser(response: UserResponse): User {
        val uuid = response.login.uuid
        val fullName = response.name.first + " " + response.name.last
        val email = response.email
        val avatarUrl = response.picture.large
        val latitude = response.location.coordinates.latitude
        val longitude = response.location.coordinates.longitude
        val isFavorite = response.isFavorite
        val gender = response.gender
        return User(uuid, fullName, email, avatarUrl, latitude, longitude, isFavorite, gender)
    }
}

fun UserDB.toUser(): User {
    return User(
        fullName = "${this.firstName} ${this.lastName}",
        email = this.email,
        avatarUrl = this.avatarUrl,
        latitude = this.latitude,
        longitude = this.longitude,
        isFavorite = this.isFavorite,
        uuid = this.uuid,
        gender = this.gender
    )
}

fun User.toUserDB(): UserDB {
    return UserDB(
        firstName = this.fullName.split(" ")[0],
        lastName = this.fullName.split(" ")[1],
        email = this.email,
        avatarUrl = this.avatarUrl,
        latitude = this.latitude,
        longitude = this.longitude,
        isFavorite = this.isFavorite,
        uuid = this.uuid,
        gender = this.gender
    )
}

