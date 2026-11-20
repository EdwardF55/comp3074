package ca.gbc.comp3074.fuller_edward.lab9.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ContactListScreen(
    onContactClick: (Int) -> Unit
){
    var uiState by remember {mutableStateOf<UsersUIState>(UsersUIState.Loading)}
    LaunchedEffect(Unit) {
        uiState = try{
            val users = RetrofitInstance.api.getUsers()
            UsersUIState.Success(users)
        }catch(e: Exception){
            UsersUIState.Error(e.message?: "Unknown Error")
        }
    }

    Scaffold(topBar = {TopAppBar(
        title = {Text(text = "Contact list")}
    )}) {
        paddingValues ->
        when(val state = uiState){
            UsersUIState.Loading -> {
                Box(
                    Modifier=Modifier.fillMaxSize().padding(paddingValues)
                    contentAlignment = Alignment.Center
                ) //Aside from completely losing track of everything, I keep getting a gradle project sync failed error.
            } //Come to build.gradle.kts and I'll show you why.
        }
    }
}

@Composable
fun ContactRow(user: User, onClick: () -> Unit) {
    ListItem(
        headlineContent = {Text (text = user.name)},
        supportingContent =
    )
}