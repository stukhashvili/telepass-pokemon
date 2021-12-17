package com.example.telepasspokemon.data.di

import com.example.telepasspokemon.data.mappers.MapperHelper
import com.example.telepasspokemon.data.mappers.MapperHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MapperModule {

    @Binds
    fun bindMapperHelper(impl: MapperHelperImpl): MapperHelper
}