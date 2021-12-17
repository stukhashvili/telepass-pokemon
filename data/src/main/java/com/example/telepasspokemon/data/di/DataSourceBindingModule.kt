package com.example.telepasspokemon.data.di

import com.example.telepasspokemon.data.datasource.PokemonRemoteDataSourceImpl
import com.example.telepasspokemon.domain.api.PokemonRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceBindingModule {

    @Binds
    fun bindPokemonRemoteDataSource(impl: PokemonRemoteDataSourceImpl): PokemonRemoteDataSource
}