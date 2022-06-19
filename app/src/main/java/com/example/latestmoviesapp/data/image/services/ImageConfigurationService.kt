package com.example.latestmoviesapp.data.image.services

import com.example.latestmoviesapp.data.image.responses.NetworkImageConfiguration
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageConfigurationService {
    @GET("configuration")
    suspend fun fetchImageConfiguration(
        @Query("api_key") apiKey: String
    ): NetworkImageConfiguration
}