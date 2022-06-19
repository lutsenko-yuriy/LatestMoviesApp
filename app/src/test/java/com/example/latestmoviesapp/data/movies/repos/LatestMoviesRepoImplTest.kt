package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.TestHelperObject.DEFAULT_COMPILE_TIME_ARGUMENTS
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_IMAGE_CONFIGURATION
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_PAGE_TO_FETCH
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_RUNTIME_ARGUMENTS
import com.example.latestmoviesapp.TestHelperObject.LATEST_RELEASE_DATE_AS_STRING
import com.example.latestmoviesapp.TestHelperObject.LOCALE_AS_STRING
import com.example.latestmoviesapp.TestHelperObject.SOME_SECURE_BASE_URL
import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.movies.responses.paging.NetworkMovie
import com.example.latestmoviesapp.data.movies.responses.paging.NetworkPagingMovies
import com.example.latestmoviesapp.data.movies.services.LatestMoviesNetworkService
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.domain.movies.MovieShortInfoPage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class LatestMoviesRepoImplTest {

    private lateinit var repo: LatestMoviesRepoImpl

    private lateinit var dummyCompileTimeArguments: NetworkCompileTimeArguments
    private lateinit var dummyRuntimeArguments: NetworkRuntimeArguments

    @Mock
    private lateinit var imageConfigurationRepo: ImageConfigurationRepo

    @Mock
    private lateinit var service: LatestMoviesNetworkService

    private lateinit var mocksDeinitializer: AutoCloseable

    @Before
    fun setUp() {
        mocksDeinitializer = MockitoAnnotations.openMocks(this)
        dummyCompileTimeArguments = DEFAULT_COMPILE_TIME_ARGUMENTS
        dummyRuntimeArguments = DEFAULT_RUNTIME_ARGUMENTS

        repo = LatestMoviesRepoImpl(
            dummyCompileTimeArguments, dummyRuntimeArguments, imageConfigurationRepo, service
        )
    }

    @After
    fun tearDown() {
        mocksDeinitializer.close()
    }

    @Test
    fun getLatestMovies_emptyList() = runTest {
        whenever(
            service.fetchLatestMovies(
                apiKey = anyString(),
                locale = any(),
                releaseDate = any(),
                order = anyString(),
                page = anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = DEFAULT_PAGE_TO_FETCH,
                movies = emptyList(),
                totalPages = 1,
                totalResults = 0
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = DEFAULT_PAGE_TO_FETCH,
            movies = emptyList(),
            totalPages = 1,
            totalResults = 0
        )
        val actualResult = repo.getLatestMovies(DEFAULT_PAGE_TO_FETCH)

        verify(service, times(1)).fetchLatestMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = LOCALE_AS_STRING,
            releaseDate = LATEST_RELEASE_DATE_AS_STRING,
            order = dummyRuntimeArguments.order,
            page = DEFAULT_PAGE_TO_FETCH
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getLatestMovies_singleMovie_posterIsPresent() = runTest {
        whenever(
            service.fetchLatestMovies(
                apiKey = anyString(),
                locale = any(),
                releaseDate = any(),
                order = anyString(),
                page = anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = DEFAULT_PAGE_TO_FETCH,
                movies = listOf(
                    NetworkMovie(
                        id = 1,
                        title = "Title",
                        posterPath = "/posterpath.jpg",
                        releaseDate = "2020-06-05",
                        voteAverage = 5,
                        adult = true,
                    )
                ),
                totalPages = 1,
                totalResults = 1
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = DEFAULT_PAGE_TO_FETCH,
            movies = listOf(
                MovieShortInfo(
                    id = 1,
                    title = "Title",
                    posterUrl = "${SOME_SECURE_BASE_URL}original/posterpath.jpg",
                    releaseDate = Calendar.getInstance().apply {
                        time = Date(2020 - 1900, 5, 5, 0, 0, 0)
                    },
                    voteAverage = 5,
                    adult = true,
                )
            ),
            totalPages = 1,
            totalResults = 1
        )
        val actualResult = repo.getLatestMovies(DEFAULT_PAGE_TO_FETCH)

        verify(service, times(1)).fetchLatestMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = LOCALE_AS_STRING,
            releaseDate = LATEST_RELEASE_DATE_AS_STRING,
            order = dummyRuntimeArguments.order,
            page = DEFAULT_PAGE_TO_FETCH
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getLatestMovies_singleMovie_posterIsAbsent() = runTest {
        whenever(
            service.fetchLatestMovies(
                apiKey = anyString(),
                locale = any(),
                releaseDate = any(),
                order = anyString(),
                page = anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = DEFAULT_PAGE_TO_FETCH,
                movies = listOf(
                    NetworkMovie(
                        id = 1,
                        title = "Title",
                        posterPath = null,
                        releaseDate = "2020-06-05",
                        voteAverage = 5,
                        adult = true,
                    )
                ),
                totalPages = 1,
                totalResults = 1
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = DEFAULT_PAGE_TO_FETCH,
            movies = listOf(
                MovieShortInfo(
                    id = 1,
                    title = "Title",
                    posterUrl = null,
                    releaseDate = Calendar.getInstance().apply {
                        time = Date(2020 - 1900, 5, 5, 0, 0, 0)
                    },
                    voteAverage = 5,
                    adult = true,
                )
            ),
            totalPages = 1,
            totalResults = 1
        )
        val actualResult = repo.getLatestMovies(DEFAULT_PAGE_TO_FETCH)

        verify(service, times(1)).fetchLatestMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = LOCALE_AS_STRING,
            releaseDate = LATEST_RELEASE_DATE_AS_STRING,
            order = dummyRuntimeArguments.order,
            page = DEFAULT_PAGE_TO_FETCH
        )

        assertEquals(expectedResult, actualResult)
    }


    @Test
    fun getLatestMovies_severalMovies_mixed() = runTest {
        whenever(
            service.fetchLatestMovies(
                apiKey = anyString(),
                locale = any(),
                releaseDate = any(),
                order = anyString(),
                page = anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = DEFAULT_PAGE_TO_FETCH,
                movies = listOf(
                    NetworkMovie(
                        id = 1,
                        title = "Title",
                        posterPath = "/posterpath.jpg",
                        releaseDate = "2020-06-05",
                        voteAverage = 5,
                        adult = true,
                    ),
                    NetworkMovie(
                        id = 1,
                        title = "Title",
                        posterPath = null,
                        releaseDate = "2020-06-04",
                        voteAverage = 5,
                        adult = false,
                    )
                ),
                totalPages = 1,
                totalResults = 2
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = DEFAULT_PAGE_TO_FETCH,
            movies = listOf(
                MovieShortInfo(
                    id = 1,
                    title = "Title",
                    posterUrl = "${SOME_SECURE_BASE_URL}original/posterpath.jpg",
                    releaseDate = Calendar.getInstance().apply {
                        time = Date(2020 - 1900, 5, 5, 0, 0, 0)
                    },
                    voteAverage = 5,
                    adult = true,
                ),
                MovieShortInfo(
                    id = 1,
                    title = "Title",
                    posterUrl = null,
                    releaseDate = Calendar.getInstance().apply {
                        time = Date(2020 - 1900, 5, 4, 0, 0, 0)
                    },
                    voteAverage = 5,
                    adult = false,
                )
            ),
            totalPages = 1,
            totalResults = 2
        )
        val actualResult = repo.getLatestMovies(DEFAULT_PAGE_TO_FETCH)

        verify(service, times(1)).fetchLatestMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = LOCALE_AS_STRING,
            releaseDate = LATEST_RELEASE_DATE_AS_STRING,
            order = dummyRuntimeArguments.order,
            page = DEFAULT_PAGE_TO_FETCH
        )

        assertEquals(expectedResult, actualResult)
    }


}