package com.example.latestmoviesapp.data.movies.responses.details


import com.google.gson.annotations.SerializedName

data class NetworkProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)