package com.example.telepasspokemon.presentation.ui.detail

import com.example.telepasspokemon.core.Mapper
import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import com.example.telepasspokemon.domain.model.PokemonTypeModel
import javax.inject.Inject

internal class DetailsModelToUiMapper @Inject constructor(

) : Mapper<PokemonDetailsModel, DetailsState.PokemonUi> {

    override fun invoke(input: PokemonDetailsModel): DetailsState.PokemonUi {
        return DetailsState.PokemonUi(
            name = input.name,
            images = input.images,
            stats = input.stats.map { stat ->
                PokemonStatUi(stat.name, stat.effort)
            },
            types = input.types.map(PokemonTypeModel::name)
        )
    }
}