package com.example.test.data.api

import com.example.test.data.api.model.Entity
import com.example.test.data.api.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(".")
    suspend fun getMovies(@Query("s") query:String, @Query("apikey") apiKey:String): SearchResponse?

    @GET(".")
    suspend fun getDetails(@Query("t") query:String, @Query("apikey") apiKey:String): Entity?

}