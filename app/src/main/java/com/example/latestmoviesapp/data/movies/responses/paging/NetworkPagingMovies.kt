package com.example.latestmoviesapp.data.movies.responses.paging


import com.google.gson.annotations.SerializedName

data class NetworkPagingMovies(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<NetworkMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)