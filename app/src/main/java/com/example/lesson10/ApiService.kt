package com.example.lesson10

import retrofit2.http.GET
import com.example.lesson10.Album
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.lesson10.ApiService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
    @get:GET("/photos")
    val allAlbums: Call<MutableList<Album>>

    companion object {
        // Link API: https://jsonplaceholder.typicode.com/photos
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
        val apiService = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}