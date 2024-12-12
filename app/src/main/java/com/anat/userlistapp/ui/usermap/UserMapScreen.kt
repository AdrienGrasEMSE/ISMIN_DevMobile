package com.anat.userlistapp.ui.usermap

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.anat.userlistapp.model.User
import com.anat.userlistapp.ui.usermap.component.FilterButton
import com.anat.userlistapp.viewmodel.UserMapViewModel
import com.anat.userlistapp.ui.usermap.component.UserDetailsContent
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMapScreen(modifier : Modifier, viewModel: UserMapViewModel = hiltViewModel()) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var isFavoritesSelected by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadUsersForMap()
    }

    val initialCameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(0.0, 0.0),
            2f
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (error) {
            Toast.makeText(context, "Error loading user data", Toast.LENGTH_SHORT).show()
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {

            val filteredUsers = if (isFavoritesSelected) {
                users.filter { it.isFavorite }
            } else {
                users
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = initialCameraPosition
            ) {
                filteredUsers.forEach { user ->
                    val lat = user.latitude.toDoubleOrNull()
                    val lng = user.longitude.toDoubleOrNull()

                    if (lat != null && lng != null) {
                        Marker(
                            state = MarkerState(position = LatLng(lat, lng)),
                            title = user.fullName,
                            snippet = user.email,
                            onInfoWindowClick = {
                                selectedUser = user
                                isBottomSheetVisible = true
                            }
                        )
                    }
                }
            }
        }
        FilterButton(
            isFavoritesSelected = isFavoritesSelected,
            onFilterChange = { isFavoritesSelected = it }
        )
    }
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                isBottomSheetVisible = false
                selectedUser = null
            }
        ) {
            selectedUser?.let { user ->
                UserDetailsContent(
                    user,
                    onDeleteClick = viewModel.deleteUser(user),
                    onFavoriteClick = viewModel.favoriteUser(user.uuid, !user.isFavorite),
                )
            }
        }
    }
}
