package ca.gbc.comp3074.fuller_edward.lab9.components

import ca.gbc.comp3074.fuller_edward.lab9.model.User

sealed interface UsersUIState {
    data object Loading: UsersUIState
    data class Success(val users: List<User>) : UsersUiState
    data class Error(val message: String) : UsersUiState
}