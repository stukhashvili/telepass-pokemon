package com.example.telepasspokemon.data.mappers

import com.example.telepasspokemon.core.Mapper
import com.example.telepasspokemon.data.dto.PokemonDto
import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import javax.inject.Inject

interface MapperHelper {

    val pokemonDetailsDtoToModelMapper: Mapper<PokemonDto, PokemonDetailsModel>
}

internal class MapperHelperImpl @Inject constructor(
    override val pokemonDetailsDtoToModelMapper: PokemonDetailsDtoToModelMapper,
) : MapperHelper