package com.anat.userlistapp.ui.userlist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.anat.userlistapp.R
import com.anat.userlistapp.model.User
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale

@Composable
fun UserDetailContent(
    isVisible: Boolean,
    user: User?,
    onDismiss: () -> Unit,
    onFavoriteClick : (isFav : Boolean) -> Unit,
) {
    if (isVisible && user != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "User Details",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(user.avatarUrl),
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = user.fullName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(text = "Favorite:", style = MaterialTheme.typography.bodyMedium)
                    Image(
                        painter = painterResource(if (user.isFavorite) R.drawable.star_filled else R.drawable.unstar),
                        contentDescription = "Favorite Icon",
                        modifier = Modifier.size(32.dp).clickable{onFavoriteClick(!user.isFavorite)},
                        colorFilter = ColorFilter.tint(if (user.isFavorite) MaterialTheme.colorScheme.primary else Color.Gray)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Gender:", style = MaterialTheme.typography.bodyMedium)
                    Image(
                        painter = painterResource(if (user.gender == "male") R.drawable.male_gender else R.drawable.female_gender),
                        contentDescription = "Gender Icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text("Close", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        )
    }
}
