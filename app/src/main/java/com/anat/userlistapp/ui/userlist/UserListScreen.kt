package com.anat.userlistapp.ui.userlist

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anat.userlistapp.viewmodel.UserListViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.anat.userlistapp.ui.theme.Dimensions
import com.anat.userlistapp.ui.userlist.component.LoadingUserListItem
import com.anat.userlistapp.ui.userlist.component.UserDetailContent
import com.anat.userlistapp.ui.userlist.component.UserListItem


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserListScreen(modifier: Modifier = Modifier, viewModel : UserListViewModel = hiltViewModel()) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isInitLoading by viewModel.isInitLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val retrying by viewModel.retrying.collectAsState()
    val listState = rememberLazyListState()
    val showDetailsPopup by viewModel.showUserDetailsPopup.collectAsState()
    val selectedUser by viewModel.selectedUser.collectAsState()

    val context = LocalContext.current
    if (error) {
        UserListErrorScreen(
            "Impossible to load data, please retry later",
            {
                if (viewModel.isNetworkAvailable(context)) {
                    viewModel.loadUsers()
                } else {
                    Toast.makeText(
                        context,
                        "Please check your Internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            retrying
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(Dimensions.Basic2.dp),
                state = listState
            ) {
                items(users) { user ->
                    UserListItem(
                        user = user,
                        onDeleteUser =  {
                            viewModel.deleteUser(user)
                        },
                        onUserClick = {
                            viewModel.showUserDetailsPopup(user)
                        }
                    )
                }

                if (isInitLoading) {
                    items(10) {
                        LoadingUserListItem()
                    }
                } else if (isLoading) {
                    items(2) {
                        LoadingUserListItem()
                    }
                }
            }

            UserDetailContent(
                isVisible = showDetailsPopup,
                user =  viewModel.selectedUser.value ,
                onDismiss = viewModel::closeUserDetailsPopup,
                onFavoriteClick = { isFav ->
                    selectedUser?.let {
                        viewModel.favoriteUser(
                            isFavorite = isFav,
                            uuid = it.uuid
                        )
                    }
                },
            )

            FloatingActionButton(
                onClick = {
                    viewModel.refresh()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Dimensions.Basic4.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Delete all users from DB and reloads new api Users")
            }
        }

        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastIndex ->
                    if (lastIndex == users.size - 1) {
                        viewModel.loadMoreUsers()
                    }
                }
        }
    }
}

@Composable
fun UserListErrorScreen(errorMessage: String, onRetry: () -> Unit, retrying: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.Basic4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(Dimensions.Basic4.dp),

                )
            if (retrying) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(Dimensions.Basic4.dp),
                    )
            } else {
                Button(onClick = onRetry) {
                    Text(text = "Retry")
                }
            }
        }
    }
}
