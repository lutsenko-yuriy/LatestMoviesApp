package com.example.latestmoviesapp

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import com.example.latestmoviesapp.domain.images.ImageConfiguration
import java.util.*

object TestHelperObject {

    const val SOME_BASE_NETWORK_URL = "http://google.com/"
    const val SOME_API_KEY = "api_key"

    val DEFAULT_COMPILE_TIME_ARGUMENTS =
        NetworkCompileTimeArguments(
            baseUrl = SOME_BASE_NETWORK_URL,
            apiKey = SOME_API_KEY
        )

    val LATEST_RELEASE_DATE = Calendar.getInstance().apply {
        set(2020, 5, 5, 0, 0, 0)
    }
    val LATEST_RELEASE_DATE_AS_STRING = "2020-06-05"

    val LOCALE = Locale.US
    val LOCALE_AS_STRING = "en-US"

    val DEFAULT_RUNTIME_ARGUMENTS =
        NetworkRuntimeArguments(
            latestReleaseDate = LATEST_RELEASE_DATE,
            locale = LOCALE,
            order = ""
        )

    const val DEFAULT_PAGE_TO_FETCH = 1

    const val SOME_SECURE_BASE_URL = "https://google.com/"
    val DEFAULT_IMAGE_CONFIGURATION = ImageConfiguration(SOME_SECURE_BASE_URL)

    const val DEFAULT_QUERY = "some query"
    const val DEFAULT_MOVIE_ID = 1234

}