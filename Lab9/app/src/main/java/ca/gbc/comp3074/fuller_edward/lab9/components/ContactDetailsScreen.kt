package ca.gbc.comp3074.fuller_edward.lab9.components

import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ca.gbc.comp3074.fuller_edward.lab9.api.RetrofitInstance

@Composable
fun ContactDetailsScreen(
    userId: Int,
    onBack: () -> Unit
){
    var user by remember {mutableStateOf<User?>(null)}
    var error by remember {mutableStateOf<String?>(null)}
    var loading by remember {mutableStateOf(true)}

    LaunchedEffect(userId){
        if(userId == null){
            error = "Invalid User Id"
            loading = false
            return@LaunchedEffect
        }
        try{
            user = RetrofitInstance.api.getUserById(userId)
        } catch (e: Exception){
            error = e.message ?: "Unknown Error"
        } finally {
            loading = false
        }
    }

    Scaffold(
        TopAppbar(title = {Text(text = "Contact Details")},
            navigationIcon = {
                IconButton(onClick = onBack) {Icon(Icons.Default.ArrowBack, contentDescription = "Back")}
            })
    ) {}
}