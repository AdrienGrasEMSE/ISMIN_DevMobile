package com.anat.userlistapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDB(
    val firstName: String,
    val lastName: String,
    val email: String,
    @PrimaryKey val uuid: String,
    val avatarUrl: String,
    val latitude : String,
    val longitude : String,
    val isFavorite : Boolean,
    val gender : String
)