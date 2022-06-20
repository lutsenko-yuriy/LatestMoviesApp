package com.example.latestmoviesapp.data.movies.responses.paging


import com.google.gson.annotations.SerializedName

data class NetworkMovie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)