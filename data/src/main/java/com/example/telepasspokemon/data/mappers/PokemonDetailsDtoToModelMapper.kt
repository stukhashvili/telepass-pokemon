package com.example.telepasspokemon.data.mappers

import com.example.telepasspokemon.core.Mapper
import com.example.telepasspokemon.data.dto.PokemonDto
import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import com.example.telepasspokemon.domain.model.PokemonStatModel
import com.example.telepasspokemon.domain.model.PokemonTypeModel
import javax.inject.Inject

internal class PokemonDetailsDtoToModelMapper @Inject constructor(

) : Mapper<PokemonDto, PokemonDetailsModel> {

    override fun invoke(input: PokemonDto): PokemonDetailsModel {
        return PokemonDetailsModel(
            name = input.name.orEmpty(),
            images = mutableListOf<String>().apply {
                input.sprites?.other?.run {
                    officialArtwork?.front?.let(::add)
                    home?.front?.let(::add)
                    home?.shiny?.let(::add)
                }
            },
            stats = input.stats?.map { stat ->
                PokemonStatModel(
                    name = stat.stat?.name.orEmpty(),
                    baseStat = stat.baseStat ?: 0,
                    effort = stat.effort ?: 0,
                )
            }.orEmpty(),
            types = input.types?.map { type ->
                PokemonTypeModel(
                    name = type.type?.name.orEmpty(),
                    slot = type.slot ?: 0,
                )
            }.orEmpty()
        )
    }
}