package com.example.telepasspokemon.domain.interactors

import com.example.telepasspokemon.domain.api.PokemonRemoteDataSource
import com.example.telepasspokemon.domain.api.PokemonRepository
import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import java.lang.Exception
import javax.inject.Inject

internal class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
) : PokemonRepository {

    override suspend fun getPokemonList(page: Int) =
        remoteDataSource.getPokemonsAtPage(page)

    override suspend fun getPokemonDetailsByName(name: String) =
        remoteDataSource.getPokemonDetailsByName(name)
}