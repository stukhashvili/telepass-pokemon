package com.example.telepasspokemon.domain.model

data class PokemonPagingModel(
    val hasNext: Boolean,
    val page: Int,
    val list: List<PokemonItemModel>,
)

data class PokemonItemModel(
    val name: String,
    val image: String,
)