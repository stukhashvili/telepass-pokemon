package com.example.telepasspokemon.presentation.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.example.telepasspokemon.core.Mapper
import com.example.telepasspokemon.domain.api.PokemonRepository
import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.android.AndroidDataFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository,
    private val detailsMapper: Mapper<PokemonDetailsModel, DetailsState.PokemonUi>
) : AndroidDataFlow(defaultState = DetailsState.Loading) {

    companion object {
        const val POKEMON_NAME = "POKEMON_NAME"
    }

    private val pokemonName = savedStateHandle.get<String>(POKEMON_NAME)
        ?: throw IllegalStateException("Pokemon name is not provided")

    init {
        load()
    }

    fun load() = action(
        onAction = {
            val details = pokemonRepository.getPokemonDetailsByName(pokemonName)
            setState { detailsMapper(details) }
        },
        onError = { error, state ->

        }
    )
}