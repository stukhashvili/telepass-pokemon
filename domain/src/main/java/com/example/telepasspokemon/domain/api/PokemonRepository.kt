package com.example.telepasspokemon.domain.api

import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import com.example.telepasspokemon.domain.model.PokemonPagingModel

interface PokemonRepository {

    suspend fun getPokemonList(page: Int): PokemonPagingModel

    suspend fun getPokemonDetailsByName(name: String): PokemonDetailsModel
}