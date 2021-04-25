package com.defconapplications.repobrowser.repository.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path



interface ApiService {
    @GET("{user}/repos?per_page=1000")
    fun getRepos(@Path(value = "user", encoded = true) user : String): Deferred<List<NetworkRepo>>

    @GET("{url}/languages")
    fun getRepoLanguages(@Path(value = "url", encoded = true) url: String): Deferred<Map<String, Int>>
}

