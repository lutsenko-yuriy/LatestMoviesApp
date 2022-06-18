package com.example.latestmoviesapp.data.movies.responses.paging


import com.google.gson.annotations.SerializedName

data class NetworkMovie(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String, // yyyy-MM-dd
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Int
)