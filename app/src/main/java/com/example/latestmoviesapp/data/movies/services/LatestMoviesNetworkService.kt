package com.example.latestmoviesapp.data.movies.services

import com.example.latestmoviesapp.data.movies.responses.paging.NetworkPagingMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestMoviesNetworkService {
    @GET("discover/movie")
    suspend fun fetchLatestMovies(
        @Query("api_key") apiKey: String,
        @Query("language") locale: String,
        @Query("primary_release_date.lte") releaseDate: String,
        @Query("sort_by") order: String,
        @Query("page") page: Int
    ): NetworkPagingMovies
}