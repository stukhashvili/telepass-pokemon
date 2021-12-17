package com.example.telepasspokemon.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class PokemonDto(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "weight")
    val weight: Int?,
    @Json(name = "sprites")
    val sprites: SpritesDto?,
    @Json(name = "stats")
    val stats: List<StatDto>?,
    @Json(name = "types")
    val types: List<TypeDto>?,
)

@JsonClass(generateAdapter = false)
data class SpritesDto(
    @Json(name = "front_default")
    val frontImage: String?,
    @Json(name = "front_female")
    val frontFemaleImage: String?,
    @Json(name = "back_default")
    val backImage: String?,
    @Json(name = "back_female")
    val backFemaleImage: String?,
    @Json(name = "other")
    val other: OtherDto?,
)

@JsonClass(generateAdapter = false)
data class OtherDto(
    @Json(name = "home")
    val home: ImagesDto?,
    @Json(name = "official-artwork")
    val officialArtwork: ImagesDto?,
)

@JsonClass(generateAdapter = false)
data class ImagesDto(
    @Json(name = "front_default")
    val front: String?,
    @Json(name = "front_shiny")
    val shiny: String?,
)

@JsonClass(generateAdapter = false)
data class StatDto(
    @Json(name = "base_stat")
    val baseStat: Int?,
    @Json(name = "effort")
    val effort: Int?,
    @Json(name = "stat")
    val stat: NamedResourceDto?,
)

@JsonClass(generateAdapter = false)
data class TypeDto(
    @Json(name = "slot")
    val slot: Int?,
    @Json(name = "type")
    val type: NamedResourceDto?,
)