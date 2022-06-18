package com.example.latestmoviesapp.data.movies.services

import com.example.latestmoviesapp.data.movies.responses.paging.NetworkPagingMovies
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDetailsNetworkService {
    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Query("api_key") apiKey: String,
        @Query("language") locale: String,
        @Field("movie_id") query: String
    ): NetworkPagingMovies
}