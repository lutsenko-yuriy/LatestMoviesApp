package com.example.latestmoviesapp.data.movies.responses.details


import com.google.gson.annotations.SerializedName

data class NetworkProductionCompany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)