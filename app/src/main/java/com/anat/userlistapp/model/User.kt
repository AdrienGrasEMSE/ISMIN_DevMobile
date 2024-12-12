package com.anat.userlistapp.model

data class User(
    val uuid : String,
    val fullName: String,
    val email: String,
    val avatarUrl: String,
    val latitude: String,
    val longitude : String,
    var isFavorite : Boolean,
    val gender : String
)