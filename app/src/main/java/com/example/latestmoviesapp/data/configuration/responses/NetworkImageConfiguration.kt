package com.example.latestmoviesapp.data.configuration.responses


import com.google.gson.annotations.SerializedName

data class NetworkImageConfiguration(
    @SerializedName("images")
    val configuration: NetworkConfiguration
)