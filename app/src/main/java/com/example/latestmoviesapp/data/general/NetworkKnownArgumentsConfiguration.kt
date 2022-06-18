package com.example.latestmoviesapp.data.general

import java.util.*

data class NetworkKnownArgumentsConfiguration(
    val apiKey: String,
    val maximalReleaseDate: Calendar,
    val locale: Locale
)