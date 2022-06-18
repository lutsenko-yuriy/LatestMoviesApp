package com.example.latestmoviesapp.data.movies.responses.details


import com.google.gson.annotations.SerializedName

data class NetworkGenre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)