package com.example.latestmoviesapp.data.movies.services

import com.example.latestmoviesapp.data.movies.responses.paging.NetworkPagingMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesByQueryNetworkService {
    @GET("search/movie")
    suspend fun fetchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") locale: String,
        @Query("query") query: String,
        @Query("sort_by") order: String,
        @Query("page") page: Int
    ): NetworkPagingMovies
}