package com.example.telepasspokemon.data.services

import com.example.telepasspokemon.data.dto.PokemonPagedDto
import com.example.telepasspokemon.data.dto.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PokemonApi {

    companion object {
        private const val DEFAULT_LIMIT = 20
    }

    @GET("pokemon")
    suspend fun requestPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
    ): PokemonPagedDto

    @GET("pokemon/{name}")
    suspend fun requestPokemonByName(
        @Path("name") name: String,
    ): PokemonDto
}