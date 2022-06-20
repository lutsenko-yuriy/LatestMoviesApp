package com.example.latestmoviesapp.data.movies.responses.details

import com.google.gson.annotations.SerializedName


data class NetworkMovieDetails(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("genres")
    val genres: List<NetworkGenre>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<NetworkProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<NetworkProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String, // yyyy-MM-dd
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)