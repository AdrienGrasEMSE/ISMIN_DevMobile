package com.anat.userlistapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anat.userlistapp.model.mapper.UserMapper
import com.anat.userlistapp.model.UserDB
import com.anat.userlistapp.repository.AppDatabase
import com.anat.userlistapp.repository.UserDao
import com.anat.userlistapp.repository.UserRepository
import com.anat.userlistapp.repository.UserRepositoryImpl
import com.anat.userlistapp.fake.FakeRemoteService
import com.anat.userlistapp.model.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var userRepository : UserRepository

    private val users = listOf(
        UserDB(
            firstName = "Anatole",
            lastName = "Rivain",
            email = "anat.rivain@gmail.com",
            avatarUrl = "https://anatole.com",
            uuid = TODO(),
            latitude = TODO(),
            longitude = TODO(),
            isFavorite = TODO(),
            gender = TODO()
        ),
        UserDB(
            firstName = "Claire",
            lastName = "Durand",
            email = "claire.durand@gmail.com",
            avatarUrl = "https://claire.com",
            uuid = TODO(),
            latitude = TODO(),
            longitude = TODO(),
            isFavorite = TODO(),
            gender = TODO()
        ),
        UserDB(
            firstName = "Lucas",
            lastName = "Martin",
            email = "lucas.martin@gmail.com",
            avatarUrl = "https://lucas.com",
            uuid = TODO(),
            latitude = TODO(),
            longitude = TODO(),
            isFavorite = TODO(),
            gender = TODO()
        ),
        UserDB(
            firstName = "Emma",
            lastName = "Bernard",
            email = "emma.bernard@gmail.com",
            avatarUrl = "https://emma.com",
            uuid = TODO(),
            latitude = TODO(),
            longitude = TODO(),
            isFavorite = TODO(),
            gender = TODO()
        ),
        UserDB(
            firstName = "Paul",
            lastName = "Leclerc",
            email = "paul.leclerc@gmail.com",
            avatarUrl = "https://paul.com",
            uuid = TODO(),
            latitude = TODO(),
            longitude = TODO(),
            isFavorite = TODO(),
            gender = TODO()
        )
    )

    @Before
     fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        userDao = db.userDao()

        userRepository = UserRepositoryImpl(
            remoteService = FakeRemoteService(),
            userDao = userDao,
            userMapper = UserMapper()
        )
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAndGetUsers() = runBlocking {
        userDao.insertUsers(users)
        val fetchedUser = userDao.getAllUsers()
        assert(fetchedUser == users)
    }

    @Test
    fun testDeleteAllUsers() = runBlocking {
        userDao.deleteAllUsers()
        assert(userDao.getAllUsers().isEmpty())
    }

    @Test
    fun testDuplicateEmails() = runBlocking {
        val duplicateUsers = listOf(
            UserDB(
                firstName = "Original",
                lastName = "User",
                email = "duplicate.email@gmail.com",
                avatarUrl = "https://original.com",
                uuid = TODO(),
                latitude = TODO(),
                longitude = TODO(),
                isFavorite = TODO(),
                gender = TODO()
            ),
            UserDB(
                firstName = "Duplicate",
                lastName = "User",
                email = "duplicate.email@gmail.com",
                avatarUrl = "https://duplicate.com",
                uuid = TODO(),
                latitude = TODO(),
                longitude = TODO(),
                isFavorite = TODO(),
                gender = TODO()
            )
        )
        userDao.deleteAllUsers()
        userDao.insertUsers(duplicateUsers)
        val fetchedUsers = userDao.getAllUsers()

        assert(fetchedUsers.size == 1)
        assert(fetchedUsers[0].avatarUrl == "https://duplicate.com")
    }

    @Test
    fun testValidateAndInsertUsers() = runBlocking {
        val usersWithNull = listOf(
            User(
                fullName = "Null User",
                email = "",
                avatarUrl = "https://null.com",
                uuid = TODO(),
                latitude = TODO(),
                longitude = TODO(),
                isFavorite = TODO(),
                gender = TODO()
            ),
            User(
                fullName = "valid User",
                email = "valid.user@gmail.com",
                avatarUrl = "https://valid.com",
                uuid = TODO(),
                latitude = TODO(),
                longitude = TODO(),
                isFavorite = TODO(),
                gender = TODO()
            )
        )
        userDao.deleteAllUsers()
        val validUsers = userRepository.validateAndInsertUsers(usersWithNull)

        assert(validUsers.size == 1) { "Expected 1 valid user, but got ${validUsers.size}" }
        assert(validUsers[0].email == "valid.user@gmail.com") { "Expected valid user email to be 'valid.user@gmail.com'" }

        val insertedUsers = userDao.getAllUsers()
        assert(insertedUsers.size == 1) { "Expected 1 user in database, but got ${insertedUsers.size}" }
        assert(insertedUsers[0].email == "valid.user@gmail.com") { "Expected email in database to be 'valid.user@gmail.com'" }
    }

    @Test
    fun testLargeDataSet() = runBlocking {
        val largeDataSet = (1..10000).map {
            UserDB(
                firstName = "FirstName$it",
                lastName = "LastName$it",
                email = "user$it@gmail.com",
                avatarUrl = "https://user$it.com",
                uuid = TODO(),
                latitude = TODO(),
                longitude = TODO(),
                isFavorite = TODO(),
                gender = TODO()
            )
        }
        userDao.insertUsers(largeDataSet)
        val fetchedUsers = userDao.getAllUsers()

        assert(fetchedUsers.size == 10000) { "Expected 10,000 users, but got ${fetchedUsers.size}" }
    }
}
