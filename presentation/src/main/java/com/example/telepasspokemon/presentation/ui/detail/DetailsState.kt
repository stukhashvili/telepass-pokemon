package com.example.telepasspokemon.presentation.ui.detail

import io.uniflow.core.flow.data.UIState

sealed class DetailsState : UIState() {
    object Loading : DetailsState()
    data class PokemonUi(
        val name: String,
        val images: List<String>,
        val stats: List<PokemonStatUi>,
        val types: List<String>
    ): DetailsState()
}

class PokemonStatUi(
    val name: String,
    val effort: Int,
)