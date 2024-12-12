package com.anat.userlistapp.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anat.userlistapp.model.UserDB

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserDB>): List<Long>

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserDB>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByUuid(email: String)

    @Query("UPDATE users SET isFavorite = :isFavorite WHERE uuid = :uuid")
    suspend fun favoriteUser(uuid: String, isFavorite: Boolean)
}