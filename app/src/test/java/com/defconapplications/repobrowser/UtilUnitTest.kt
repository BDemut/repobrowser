package com.defconapplications.repobrowser

import com.defconapplications.repobrowser.repository.network.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class UtilUnitTest {

    @Test
    fun formatCreatedDateTest(){
        val str = "2016-10-24T11:32:11Z"
        val dateStr = formatCreatedDate(str)
        assertEquals("October 24, 2016", dateStr)
    }

    @Test
    fun formatUpdatedDateTest(){
        val str = "2021-04-16T11:43:28Z"
        val dateStr = formatUpdatedDate(str)
        print(dateStr)
    }

}