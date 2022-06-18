package com.example.latestmoviesapp.data.movies.responses.details


import com.google.gson.annotations.SerializedName

data class NetworkProductionCountry(
    @SerializedName("name")
    val name: String
)