package com.example.telepasspokemon.data.datasource

import com.example.telepasspokemon.data.mappers.MapperHelper
import com.example.telepasspokemon.data.services.PokemonApi
import com.example.telepasspokemon.domain.api.PokemonRemoteDataSource
import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import com.example.telepasspokemon.domain.model.PokemonItemModel
import com.example.telepasspokemon.domain.model.PokemonPagingModel
import javax.inject.Inject

internal class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val mapperHelper: MapperHelper,
) : PokemonRemoteDataSource {

    override suspend fun getPokemonsAtPage(page: Int): PokemonPagingModel {
        val offset = page * PokemonApi.DEFAULT_LIMIT
        return pokemonApi.requestPokemons(offset).let { dto ->
            PokemonPagingModel(
                hasNext = dto.next != null,
                page = page,
                list = dto.results?.map { pokemonDto ->
                    val pokemon = pokemonApi.requestPokemonByName(pokemonDto.name)
                    PokemonItemModel(
                        name = pokemonDto.name,
                        image = pokemon.sprites?.frontImage.orEmpty()
                    )
                }.orEmpty()
            )
        }
    }

    override suspend fun getPokemonDetailsByName(name: String): PokemonDetailsModel {
        return pokemonApi.requestPokemonByName(name)
            .let(mapperHelper.pokemonDetailsDtoToModelMapper::invoke)
    }
}