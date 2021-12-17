package com.example.telepasspokemon.presentation.di

import com.example.telepasspokemon.core.Mapper
import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import com.example.telepasspokemon.presentation.ui.detail.DetailsModelToUiMapper
import com.example.telepasspokemon.presentation.ui.detail.DetailsState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface PresentationBindingModule {

    @Binds
    fun bindDetailsModelToUiMapper(
        impl: DetailsModelToUiMapper
    ): Mapper<PokemonDetailsModel, DetailsState.PokemonUi>
}