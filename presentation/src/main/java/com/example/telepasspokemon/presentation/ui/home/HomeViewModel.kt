package com.example.telepasspokemon.presentation.ui.home

import com.example.telepasspokemon.domain.api.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.android.AndroidDataFlow
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : AndroidDataFlow(defaultState = HomeState.Initial) {

    init {
        loadNext()
    }

    fun loadNext() = action(
        onAction = { state ->
            when (state) {
                is HomeState.Initial -> loadNextPage(HomeState.Page.initial())
                is HomeState.Page -> if (state.hasNext) loadNextPage(state)
                is HomeState.Error -> loadNextPage(state.page)
            }
        },
        onError = { _, state ->
            setState {
                HomeState.Error(state as? HomeState.Page ?: HomeState.Page.initial())
            }
        }
    )

    private suspend fun loadNextPage(prev: HomeState.Page) {
        if (prev.pokemonList.isEmpty()) setState(HomeState.Initial)
        val pageModel = pokemonRepository.getPokemonList(prev.nextPage)
        setState {
            HomeState.Page(
                hasNext = pageModel.hasNext,
                page = pageModel.page,
                pokemonList = prev.pokemonList + pageModel.list.map { pokemonModel ->
                    PokemonItemUi(pokemonModel.name, pokemonModel.image)
                }
            )
        }
    }
}