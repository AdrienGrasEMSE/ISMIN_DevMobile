package com.anat.userlistapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anat.userlistapp.fake.FakeUserRepository
import com.anat.userlistapp.model.User
import com.anat.userlistapp.repository.AppDatabase
import com.anat.userlistapp.repository.UserRepository
import com.anat.userlistapp.viewmodel.UserListViewModel
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserListViewModelTest : TestCase() {

    private val oneSecDelayMs :Long = 1000

    private lateinit var viewModel : UserListViewModel
    private lateinit var userRepository: UserRepository

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        userRepository = FakeUserRepository()

        viewModel = UserListViewModel(userRepository)
    }

    @Test
    fun initialStateIsCorrect() {
        assertEquals(emptyList<User>(), viewModel.users.value)
        assertFalse(viewModel.error.value)
        assertFalse(viewModel.isLoading.value)
        assertFalse(viewModel.retrying.value)
    }

    @Test
    fun loadUsersUpdatesStateOnSuccess() = runBlocking {
        val usersList = listOf(
            User(fullName = "John Doe", email = "john.doe@example.com", avatarUrl = "url1"),
            User(fullName = "Jane Smith", email = "jane.smith@example.com", avatarUrl = "url2")
        )
        val fakeRepository = userRepository as FakeUserRepository
        fakeRepository.setReturnError(false)

        viewModel.loadUsers()
        delay(oneSecDelayMs)

        assertEquals(usersList, viewModel.users.value)
        assertFalse(viewModel.error.value)
        assertFalse(viewModel.isLoading.value)
        assertFalse(viewModel.retrying.value)
    }

    @Test
    fun loadUsersUpdatesStateOnError() = runBlocking {
        val fakeRepository = userRepository as FakeUserRepository
        fakeRepository.setReturnError(true)

        viewModel.loadUsers()
        delay(oneSecDelayMs)

        assertTrue(viewModel.error.value)
        assertFalse(viewModel.isLoading.value)
        assertFalse(viewModel.retrying.value)
        assertTrue(viewModel.users.value.isEmpty())
    }

    @Test
    fun loadUsersUpdatesLoadingState() = runBlocking {
        val fakeRepository = userRepository as FakeUserRepository
        fakeRepository.setReturnError(false)

        viewModel.loadUsers()

        assertTrue(viewModel.isLoading.value)

        delay(oneSecDelayMs)
        assertFalse(viewModel.isLoading.value)
    }


   @Test
   fun retryAfterErrorUpdatesStateCorrectly() = runBlocking {
       val fakeRepository = userRepository as FakeUserRepository

       fakeRepository.setReturnError(true)
       fakeRepository.setUsers(listOf( User(fullName = "John Doe", email = "john.doe@example.com", avatarUrl = "url1"),
           User(fullName = "Jane Smith", email = "jane.smith@example.com", avatarUrl = "url2")
       ))

       viewModel.loadUsers()
       delay(oneSecDelayMs)

       assertTrue(viewModel.error.value)
       assertFalse(viewModel.retrying.value)

       fakeRepository.setReturnError(false)

       viewModel.refresh()
       assertTrue(viewModel.retrying.value)

       delay(oneSecDelayMs)

       assertFalse(viewModel.error.value)
       assertFalse(viewModel.retrying.value)
       assertFalse(viewModel.isLoading.value)
       assertTrue(viewModel.users.value.isEmpty())
   }


    @Test
    fun loadUsersHandlesEmptyList() = runBlocking {
        val fakeRepository = userRepository as FakeUserRepository
        fakeRepository.setReturnError(false)
        fakeRepository.setEmptyUserList(true)
        viewModel = UserListViewModel(fakeRepository)

        viewModel.loadUsers()
        delay(oneSecDelayMs)

        assertTrue(viewModel.users.value.isEmpty())
        assertTrue(viewModel.error.value)
        assertFalse(viewModel.isLoading.value)
    }


    @Test
    fun loadMoreUsersTest() = runBlocking {

        val fakeRepository = userRepository as FakeUserRepository
        fakeRepository.deleteAllUsers()
        viewModel = UserListViewModel(fakeRepository)

        val initialUsers = listOf(
            User(fullName = "John Doe", email = "john.doe@example.com", avatarUrl = "url1")
        )

        val moreUsers = listOf(
            User(fullName = "Jane Smith", email = "jane.smith@example.com", avatarUrl = "url2")
        )

        fakeRepository.setUsers(initialUsers)
        delay(oneSecDelayMs)

        viewModel.loadUsers()
        delay(oneSecDelayMs)

        val usersAfterFirstLoad = viewModel.users.value
        assertEquals(initialUsers, usersAfterFirstLoad)

        fakeRepository.setUsers(moreUsers)

        viewModel.loadMoreUsers()
        delay(oneSecDelayMs)

        val usersAfterSecondLoad = viewModel.users.value
        assertEquals(initialUsers + moreUsers, usersAfterSecondLoad)
    }
}
