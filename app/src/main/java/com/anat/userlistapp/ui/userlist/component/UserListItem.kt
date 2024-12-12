package com.anat.userlistapp.ui.userlist.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.anat.userlistapp.model.User
import com.anat.userlistapp.ui.theme.Dimensions
import com.anat.userlistapp.ui.theme.Gray
import com.anat.userlistapp.ui.theme.LightGray

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun UserListItem(
    user: User,
    onDeleteUser: (User) -> Unit,
    onUserClick: () -> Unit
) {
    var isSwiped by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < -20) {
                        isSwiped = true
                    } else if (dragAmount > 20) {
                        isSwiped = false
                    }
                }
            }
    ) {
        if (isSwiped) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.error)
                    .padding(end = 16.dp)
                    .align(Alignment.CenterEnd)
                    .fillMaxWidth(0.15f),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = {
                    onDeleteUser(user)
                    isSwiped = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete User",
                        tint = Color.White
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Basic4.dp)
                .graphicsLayer {
                    translationX = if (isSwiped) (-Dimensions.Basic4.dp).toPx() else 0f
                }
                .clickable { onUserClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = user.avatarUrl),
                contentDescription = "User Photo",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray
                )
            }
        }
    }
}

