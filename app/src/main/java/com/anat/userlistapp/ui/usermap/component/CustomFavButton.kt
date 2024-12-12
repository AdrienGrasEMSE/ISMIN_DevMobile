package com.anat.userlistapp.ui.usermap.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterButton(
    isFavoritesSelected: Boolean,
    onFilterChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.BottomCenter),
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = RoundedCornerShape(50),
            color = Color.White
        ) {
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (!isFavoritesSelected) Color.Black else Color.White,
                    modifier = Modifier
                        .clickable { onFilterChange(false) }
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "All",
                            color = if (!isFavoritesSelected) Color.White else Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isFavoritesSelected) Color.Black else Color.White,
                    modifier = Modifier
                        .clickable { onFilterChange(true) }
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Favorites",
                            color = if (isFavoritesSelected) Color.White else Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
