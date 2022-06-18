package com.example.latestmoviesapp.data.responses.images


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesConfiguration(
    @SerialName("images")
    val configuration: Images
)