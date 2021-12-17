package com.example.telepasspokemon.core

interface Mapper<IN, OUT> {

    operator fun invoke(input: IN): OUT
}