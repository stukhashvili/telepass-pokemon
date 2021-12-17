package com.example.telepasspokemon.domain.interactors

import com.example.telepasspokemon.domain.api.PokemonRemoteDataSource
import com.example.telepasspokemon.domain.model.PokemonDetailsModel
import com.example.telepasspokemon.domain.model.PokemonItemModel
import com.example.telepasspokemon.domain.model.PokemonPagingModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class PokemonRepositoryImplTest {

    private val remoteDataSource: PokemonRemoteDataSource = mock()
    private val repository = PokemonRepositoryImpl(remoteDataSource)
    private val page = 0
    private val name = "Pokemon"
    private val pagingModel = PokemonPagingModel(
        hasNext = true,
        page = page,
        list = listOf(
            PokemonItemModel(name, "image_url")
        )
    )
    private val detailsModel = PokemonDetailsModel(
        name = name,
        images = listOf("image1", "image2"),
        stats = emptyList(),
        types = emptyList(),
    )

    @Test
    fun testGetPokemonList_success(): Unit = runBlocking {
        whenever(remoteDataSource.getPokemonsAtPage(page)).thenReturn(pagingModel)

        val data = repository.getPokemonList(page)

        verify(remoteDataSource).getPokemonsAtPage(page)
        Assert.assertEquals(pagingModel, data)
    }

    @Test(expected = Exception::class)
    fun testGetPokemonList_fail(): Unit = runBlocking {
        whenever(remoteDataSource.getPokemonsAtPage(page)).thenThrow(Exception("error"))

        repository.getPokemonList(page)
    }

    @Test
    fun testGetPokemonDetailsByName_success(): Unit = runBlocking {
        whenever(remoteDataSource.getPokemonDetailsByName(name))
            .thenReturn(detailsModel)

        val data = repository.getPokemonDetailsByName(name)

        verify(remoteDataSource).getPokemonDetailsByName(name)
        Assert.assertEquals(detailsModel, data)
    }

    @Test(expected = Exception::class)
    fun testGetPokemonDetailsByName_fail(): Unit = runBlocking {
        whenever(remoteDataSource.getPokemonDetailsByName(name))
            .thenThrow(Exception("error"))

        repository.getPokemonDetailsByName(name)
    }
}