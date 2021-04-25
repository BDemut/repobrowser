package com.defconapplications.repobrowser

import com.defconapplications.repobrowser.repository.network.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiUnitTest {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl( "https://api.github.com/users/")
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    @Test
    fun getReposSucceeds () = runBlocking {
        println(apiService.getRepos("allegro").await())
        assert(true)
    }

    @Test
    fun getLangsSucceeds () = runBlocking {
        println(apiService.getRepoLanguages("https://api.github.com/repos/allegro/akubra").await())
        assert(true)
    }

    @Test
    fun parseLanguagesTest() = runBlocking {
        val response = apiService.getRepoLanguages("https://api.github.com/repos/allegro/akubra").await()
        val list = parseLanguages(response)
        assertEquals(listOf("Go: 99.68%", "Makefile: 0.32%"),list)
    }
}