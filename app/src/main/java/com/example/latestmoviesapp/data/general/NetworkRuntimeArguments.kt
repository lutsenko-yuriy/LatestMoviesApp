package com.example.latestmoviesapp.data.general

import java.util.*

data class NetworkRuntimeArguments(
    val latestReleaseDate: Calendar,
    val locale: Locale,
    val order: String = "release_date.desc"
)