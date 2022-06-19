package com.example.latestmoviesapp.domain.movies

data class MovieProductionCompany(
    val id: Int,
    val logoUrl: String?,
    val name: String,
    val originCountry: String
)