package com.example.latestmoviesapp.data.paging


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPagingMovies(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val movies: List<NetworkMovie>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)