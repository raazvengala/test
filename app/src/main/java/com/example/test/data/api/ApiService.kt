package com.example.test.data.api

import com.example.test.data.api.model.Entity
import com.example.test.data.api.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(".")
    fun getEntities(@Query("s") query: String, @Query("apikey") apiKey: String): Call<SearchResponse?>

    @GET(".")
    fun getDetails(@Query("t") query: String, @Query("apikey") apiKey: String): Call<Entity?>

}