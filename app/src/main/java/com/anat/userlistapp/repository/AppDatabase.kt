package com.anat.userlistapp.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anat.userlistapp.model.UserDB

@Database(entities = [UserDB::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
