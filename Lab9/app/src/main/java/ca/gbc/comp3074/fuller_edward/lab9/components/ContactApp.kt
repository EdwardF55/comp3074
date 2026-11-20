package ca.gbc.comp3074.fuller_edward.lab9.components

import androidx.compose.runtime.Composable

@Composable
fun ContactApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "contacts"){
        composable ("contacts"){
            ContactListScreen{userId -> navController.navigate("details/$userId")}
        }

        composable("details/{userId}"){
            backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()

            ContactDetailsScreen(userId = userId, onBack = {navController.popbackStack()})
        }
    }
}