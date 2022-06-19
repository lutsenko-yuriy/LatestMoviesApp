package com.example.latestmoviesapp.data.image.responses


import com.google.gson.annotations.SerializedName

data class NetworkImageConfiguration(
    @SerializedName("images")
    val configuration: NetworkConfiguration
)