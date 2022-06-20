package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.TestHelperObject
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_COMPILE_TIME_ARGUMENTS
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_IMAGE_CONFIGURATION
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_MOVIE_ID
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_RUNTIME_ARGUMENTS
import com.example.latestmoviesapp.TestHelperObject.LOCALE_AS_STRING
import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.movies.responses.details.NetworkGenre
import com.example.latestmoviesapp.data.movies.responses.details.NetworkMovieDetails
import com.example.latestmoviesapp.data.movies.responses.details.NetworkProductionCompany
import com.example.latestmoviesapp.data.movies.responses.details.NetworkProductionCountry
import com.example.latestmoviesapp.data.movies.services.MovieDetailsNetworkService
import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo
import com.example.latestmoviesapp.domain.movies.MovieGenre
import com.example.latestmoviesapp.domain.movies.MovieProductionCompany
import com.example.latestmoviesapp.domain.movies.MovieProductionCountry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailedInfoRepoImplTest {

    private lateinit var repo: MovieDetailedInfoRepoImpl

    private lateinit var dummyCompileTimeArguments: NetworkCompileTimeArguments
    private lateinit var dummyRuntimeArguments: NetworkRuntimeArguments

    @Mock
    private lateinit var imageConfigurationRepo: ImageConfigurationRepo

    @Mock
    private lateinit var service: MovieDetailsNetworkService

    private lateinit var mocksDeinitializer: AutoCloseable

    @Before
    fun setUp() {
        mocksDeinitializer = MockitoAnnotations.openMocks(this)
        dummyCompileTimeArguments = DEFAULT_COMPILE_TIME_ARGUMENTS
        dummyRuntimeArguments = DEFAULT_RUNTIME_ARGUMENTS

        repo = MovieDetailedInfoRepoImpl(
            dummyCompileTimeArguments, dummyRuntimeArguments, imageConfigurationRepo, service
        )
    }

    @After
    fun tearDown() {
        mocksDeinitializer.close()
    }

    @Test
    fun getMovieDetailsById() = runTest {
        whenever(
            service.fetchMovieDetails(
                movieId = anyInt(),
                apiKey = ArgumentMatchers.anyString(),
                locale = any()
            )
        ).thenReturn(
            NetworkMovieDetails(
                id = DEFAULT_MOVIE_ID,
                title = "Title",
                originalTitle = "Original Title",
                originalLanguage = "English",
                posterPath = "/backdropPath.png",
                tagline = "Tagline",
                releaseDate = "2020-06-05",
                voteAverage = 5.5,
                voteCount = 404,
                genres = listOf(
                    NetworkGenre(
                        id = 1,
                        name = "Some genre"
                    ),
                    NetworkGenre(
                        id = 2,
                        name = "Some other genre"
                    )
                ),
                overview = "Overview",
                homepage = "https://google.com",
                imdbId = "imdb_id",
                productionCompanies = listOf(
                    NetworkProductionCompany(
                        id = 1,
                        logoPath = "/logoPath.jpg",
                        name = "Some name",
                        originCountry = "Russia"
                    ),
                    NetworkProductionCompany(
                        id = 2,
                        logoPath = "/logoPath2.jpg",
                        name = "Some other name",
                        originCountry = "Germany"
                    ),
                ),
                productionCountries = listOf(
                    NetworkProductionCountry(
                        "Russia"
                    ),
                    NetworkProductionCountry(
                        "Germany"
                    )
                ),
                adult = false,
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieDetailedInfo(
            id = DEFAULT_MOVIE_ID,
            title = "Title",
            originalTitle = "Original Title",
            originalLanguage = "English",
            posterUrl = "${TestHelperObject.SOME_SECURE_BASE_URL}original/backdropPath.png",
            tagline = "Tagline",
            releaseDate = Calendar.getInstance().apply {
                time = Date(2020 - 1900, 5, 5, 0, 0, 0)
            },
            voteAverage = 5.5,
            voteCount = 404,
            genres = listOf(
                MovieGenre(
                    id = 1,
                    name = "Some genre"
                ),
                MovieGenre(
                    id = 2,
                    name = "Some other genre"
                )
            ),
            overview = "Overview",
            homepage = "https://google.com",
            imdbId = "imdb_id",
            productionCompanies = listOf(
                MovieProductionCompany(
                    id = 1,
                    logoUrl = "${TestHelperObject.SOME_SECURE_BASE_URL}original/logoPath.jpg",
                    name = "Some name",
                    originCountry = "Russia"
                ),
                MovieProductionCompany(
                    id = 2,
                    logoUrl = "${TestHelperObject.SOME_SECURE_BASE_URL}original/logoPath2.jpg",
                    name = "Some other name",
                    originCountry = "Germany"
                ),
            ),
            productionCountries = listOf(
                MovieProductionCountry(
                    "Russia"
                ),
                MovieProductionCountry(
                    "Germany"
                )
            ),
            adult = false,
        )

        val actualResult = repo.getMovieDetailedInfo(DEFAULT_MOVIE_ID)

        verify(service, times(1)).fetchMovieDetails(
            movieId = DEFAULT_MOVIE_ID,
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = LOCALE_AS_STRING,
        )

        Assertions.assertEquals(expectedResult, actualResult)
    }


    @Test
    fun getMovieDetailsById_allNullableFieldsAreNull() = runTest {
        whenever(
            service.fetchMovieDetails(
                movieId = anyInt(),
                apiKey = ArgumentMatchers.anyString(),
                locale = any()
            )
        ).thenReturn(
            NetworkMovieDetails(
                id = DEFAULT_MOVIE_ID,
                title = "Title",
                originalTitle = "Original Title",
                originalLanguage = "English",
                posterPath = null,
                tagline = null,
                releaseDate = "2020-06-05",
                voteAverage = 5.5,
                voteCount = 404,
                genres = listOf(
                    NetworkGenre(
                        id = 1,
                        name = "Some genre"
                    ),
                    NetworkGenre(
                        id = 2,
                        name = "Some other genre"
                    )
                ),
                overview = "Overview",
                homepage = "https://google.com",
                imdbId = null,
                productionCompanies = listOf(
                    NetworkProductionCompany(
                        id = 1,
                        logoPath = null,
                        name = "Some name",
                        originCountry = "Russia"
                    ),
                    NetworkProductionCompany(
                        id = 2,
                        logoPath = null,
                        name = "Some other name",
                        originCountry = "Germany"
                    ),
                ),
                productionCountries = listOf(
                    NetworkProductionCountry(
                        "Russia"
                    ),
                    NetworkProductionCountry(
                        "Germany"
                    )
                ),
                adult = true,
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieDetailedInfo(
            id = DEFAULT_MOVIE_ID,
            title = "Title",
            originalTitle = "Original Title",
            originalLanguage = "English",
            posterUrl = null,
            tagline = null,
            releaseDate = Calendar.getInstance().apply {
                time = Date(2020 - 1900, 5, 5, 0, 0, 0)
            },
            voteAverage = 5.5,
            voteCount = 404,
            genres = listOf(
                MovieGenre(
                    id = 1,
                    name = "Some genre"
                ),
                MovieGenre(
                    id = 2,
                    name = "Some other genre"
                )
            ),
            overview = "Overview",
            homepage = "https://google.com",
            imdbId = null,
            productionCompanies = listOf(
                MovieProductionCompany(
                    id = 1,
                    logoUrl = null,
                    name = "Some name",
                    originCountry = "Russia"
                ),
                MovieProductionCompany(
                    id = 2,
                    logoUrl = null,
                    name = "Some other name",
                    originCountry = "Germany"
                ),
            ),
            productionCountries = listOf(
                MovieProductionCountry(
                    "Russia"
                ),
                MovieProductionCountry(
                    "Germany"
                )
            ),
            adult = true,
        )

        val actualResult = repo.getMovieDetailedInfo(DEFAULT_MOVIE_ID)

        verify(service, times(1)).fetchMovieDetails(
            movieId = DEFAULT_MOVIE_ID,
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = LOCALE_AS_STRING,
        )

        Assertions.assertEquals(expectedResult, actualResult)
    }


    @Test
    fun getMovieDetailsById_listsAreEmpty() = runTest {
        whenever(
            service.fetchMovieDetails(
                movieId = anyInt(),
                apiKey = ArgumentMatchers.anyString(),
                locale = any()
            )
        ).thenReturn(
            NetworkMovieDetails(
                id = DEFAULT_MOVIE_ID,
                title = "Title",
                originalTitle = "Original Title",
                originalLanguage = "English",
                posterPath = "/backdropPath.png",
                tagline = "Tagline",
                releaseDate = "2020-06-05",
                voteAverage = 5.5,
                voteCount = 404,
                genres = emptyList(),
                overview = "Overview",
                homepage = "https://google.com",
                imdbId = "imdb_id",
                productionCompanies = emptyList(),
                productionCountries = emptyList(),
                adult = false,
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieDetailedInfo(
            id = DEFAULT_MOVIE_ID,
            title = "Title",
            originalTitle = "Original Title",
            originalLanguage = "English",
            posterUrl = "${TestHelperObject.SOME_SECURE_BASE_URL}original/backdropPath.png",
            tagline = "Tagline",
            releaseDate = Calendar.getInstance().apply {
                time = Date(2020 - 1900, 5, 5, 0, 0, 0)
            },
            voteAverage = 5.5,
            voteCount = 404,
            genres = emptyList(),
            overview = "Overview",
            homepage = "https://google.com",
            imdbId = "imdb_id",
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            adult = false,
        )

        val actualResult = repo.getMovieDetailedInfo(DEFAULT_MOVIE_ID)

        verify(service, times(1)).fetchMovieDetails(
            movieId = DEFAULT_MOVIE_ID,
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = LOCALE_AS_STRING,
        )

        Assertions.assertEquals(expectedResult, actualResult)
    }

}