package ca.gbc.comp3074.fuller_edward.lab9.api

import retrofit2.http.GET
import ca.gbc.comp3074.fuller_edward.lab9.model.User

interface JsonPlaceholderApi {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserById(id: Int): User
}