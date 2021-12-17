package com.example.telepasspokemon.presentation.di

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal class PresentationModule {

    @Provides
    @FragmentScoped
    fun provideGlide(fragment: Fragment): RequestManager {
        return Glide.with(fragment)
    }
}