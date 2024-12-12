package com.anat.userlistapp.ui.appinfo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppInfoScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold {
        val scrollState = rememberScrollState()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Developers:",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Anatole Rivain      --      Adrien Gras",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Api used:",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = AnnotatedString("https://randomuser.me/"),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Our Clever Cloud Api:",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = AnnotatedString("https://app-f94ff18e-2c86-4a83-9fe4-857eafc04801.cleverapps.io"),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Explanations:",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "This is a simple app displaying a list of Users that we got from randomUsers API.\n" +
                        "They have locations so that we can place them on a map.\n" +
                        "Some of them are on the boat lol.\n" +
                        "This app could be a Snapchat feature where you can locate your friends on a map for example.",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Main libraries used :",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "UI and Design",
                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = "Jetpack Compose and Material 3 for a modern and dynamic UI.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Dependency Injection",
                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = "Hilt simplifies dependency injection and ViewModel integration.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Navigation",
                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = "Navigation-Compose for seamless screen transitions.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Networking",
                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = "Retrofit for API calls and Gson for JSON parsing.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Google Maps",
                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = "Maps-Compose and Play Services for interactive maps.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Database",
                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = "Room for local database management.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                    text = "Lifecycle Management",
            style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = "Android Lifecycle and ViewModel integration.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))


            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
fun LibraryInfoItem(title: String, description: String) {
    Column( horizontalAlignment = Alignment.Start) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium

        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}