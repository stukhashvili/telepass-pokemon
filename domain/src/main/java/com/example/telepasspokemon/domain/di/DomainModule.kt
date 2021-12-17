package com.example.telepasspokemon.domain.di

import com.example.telepasspokemon.domain.api.PokemonRepository
import com.example.telepasspokemon.domain.interactors.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface DomainModule {

    @Binds
    fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository
}