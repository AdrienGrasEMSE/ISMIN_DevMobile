package com.anat.userlistapp.ui.userlist.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anat.userlistapp.ui.theme.Dimensions
import com.anat.userlistapp.ui.theme.Gray
import com.anat.userlistapp.ui.theme.LightGray


@Composable
fun LoadingUserListItem() {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffsetX = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Basic4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(LightGray, Gray, LightGray),
                        start = Offset(animatedOffsetX.value, 0f),
                        end = Offset(animatedOffsetX.value + 100f, 100f)
                    )
                )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(16.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(LightGray, Gray, LightGray),
                            start = Offset(animatedOffsetX.value, 0f),
                            end = Offset(animatedOffsetX.value + 100f, 100f)
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(12.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(LightGray, Gray, LightGray),
                            start = Offset(animatedOffsetX.value, 0f),
                            end = Offset(animatedOffsetX.value + 100f, 100f)
                        )
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingUserListItemPreview() {
    MaterialTheme {
        LoadingUserListItem()
    }
}