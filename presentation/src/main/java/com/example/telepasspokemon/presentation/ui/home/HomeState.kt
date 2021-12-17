package com.example.telepasspokemon.presentation.ui.home

import io.uniflow.core.flow.data.UIState

sealed class HomeState : UIState() {
    object Initial : HomeState()
    data class Page(
        val hasNext: Boolean,
        val page: Int,
        val pokemonList: List<PokemonItemUi>
    ) : HomeState() {

        val nextPage get() = page + 1

        companion object {

            fun initial() = Page(true, -1, emptyList())
        }
    }
    data class Error(
        val page: Page
    ): HomeState()
}

data class PokemonItemUi(
    val name: String,
    val image: String,
) {
    var isLoadingPlaceHolder = false

    companion object {
        fun loadingPlaceholder() = PokemonItemUi("", "").apply {
            isLoadingPlaceHolder = true
        }
    }
}

