package com.example.latestmoviesapp.data.movies.services

import com.example.latestmoviesapp.data.movies.responses.details.NetworkMovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsNetworkService {
    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") locale: String
    ): NetworkMovieDetails
}