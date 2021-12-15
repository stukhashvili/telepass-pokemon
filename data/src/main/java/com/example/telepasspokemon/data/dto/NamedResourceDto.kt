package com.example.telepasspokemon.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NamedResourceDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)