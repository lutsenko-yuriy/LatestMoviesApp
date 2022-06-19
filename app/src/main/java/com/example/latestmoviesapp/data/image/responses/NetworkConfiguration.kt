package com.example.latestmoviesapp.data.image.responses

import com.google.gson.annotations.SerializedName

data class NetworkConfiguration(
    @SerializedName("secure_base_url")
    val secureBaseUrl: String
)