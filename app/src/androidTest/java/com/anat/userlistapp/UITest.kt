package com.anat.userlistapp

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.anat.userlistapp.fake.FakeUserRepository
import com.anat.userlistapp.ui.theme.UserListAppTheme
import com.anat.userlistapp.viewmodel.UserListViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
/*
//@HiltAndroidTest
class UserListAppTest {

    //@get:Rule(order = 0)
    //val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 0)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    //@BindValue
    lateinit var fakeRepository: FakeUserRepository
    lateinit var userListViewModel: UserListViewModel

    @Before
    fun setUp() {
        fakeRepository = FakeUserRepository()
        userListViewModel = UserListViewModel(fakeRepository)
    }

    private fun testingEnv(){
    }

    @Test
    fun simpleUITest() {

        composeTestRule.setContent {
            UserListAppTheme {
                UserListApp()
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 100000) {
            composeTestRule.onAllNodesWithText("UserListApp").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("User List")
            .assertIsDisplayed()
    }


    @Test
    fun displayErrorMessage_whenRepositoryReturnsEmptyList() {
        /*// Simulez que le repository renvoie une liste vide
        whenever(fakeRepository.getUsers()).thenReturn(emptyList())

        // Rendre le composant
        composeTestRule.setContent {
            UserListApp(repository = fakeRepository) // Passez le repository mocké
        }

        // Vérifiez que le texte "vérifiez votre connexion internet" est affiché
        composeTestRule
            .onNodeWithText("vérifiez votre connexion internet") // Localise le texte
            .assertIsDisplayed() // Vérifie qu'il est affiché*/

    }
}*/