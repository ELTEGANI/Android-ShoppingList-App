package com.example.tddconcepts.data.remote

import com.example.tddconcepts.BuildConfig
import com.example.tddconcepts.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PixalByApi {
   @GET("/api/")
   suspend fun searchForImage(
       @Query("q") searchQuery: String,
       @Query("key") apiKey:String = BuildConfig.API_KEY
   ):Response<ImageResponse>
}