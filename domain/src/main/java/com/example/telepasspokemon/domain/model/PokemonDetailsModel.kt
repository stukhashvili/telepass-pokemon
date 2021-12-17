package com.example.telepasspokemon.domain.model

data class PokemonDetailsModel(
    val name: String,
    val images: List<String>,
    val stats: List<PokemonStatModel>,
    val types: List<PokemonTypeModel>,
)

data class PokemonStatModel(
    val name: String,
    val baseStat: Int,
    val effort: Int,
)

data class PokemonTypeModel(
    val name: String,
    val slot: Int,
)