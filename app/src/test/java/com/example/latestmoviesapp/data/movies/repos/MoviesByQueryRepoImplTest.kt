package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.TestHelperObject
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_QUERY
import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.movies.responses.paging.NetworkMovie
import com.example.latestmoviesapp.data.movies.responses.paging.NetworkPagingMovies
import com.example.latestmoviesapp.data.movies.services.MoviesByQueryNetworkService
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.domain.movies.MovieShortInfoPage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class MoviesByQueryRepoImplTest {

    private lateinit var repo: MoviesByQueryRepoImpl

    private lateinit var dummyCompileTimeArguments: NetworkCompileTimeArguments
    private lateinit var dummyRuntimeArguments: NetworkRuntimeArguments

    @Mock
    private lateinit var imageConfigurationRepo: ImageConfigurationRepo

    @Mock
    private lateinit var service: MoviesByQueryNetworkService

    private lateinit var mocksDeinitializer: AutoCloseable

    @Before
    fun setUp() {
        mocksDeinitializer = MockitoAnnotations.openMocks(this)
        dummyCompileTimeArguments = TestHelperObject.DEFAULT_COMPILE_TIME_ARGUMENTS
        dummyRuntimeArguments = TestHelperObject.DEFAULT_RUNTIME_ARGUMENTS

        repo = MoviesByQueryRepoImpl(
            dummyCompileTimeArguments, dummyRuntimeArguments, imageConfigurationRepo, service
        )
    }

    @After
    fun tearDown() {
        mocksDeinitializer.close()
    }

    @Test
    fun getMoviesByQuery_emptyList() = runTest {
        whenever(
            service.fetchMovies(
                apiKey = ArgumentMatchers.anyString(),
                locale = any(),
                query = any(),
                order = ArgumentMatchers.anyString(),
                page = ArgumentMatchers.anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = TestHelperObject.DEFAULT_PAGE_TO_FETCH,
                movies = emptyList(),
                totalPages = 1,
                totalResults = 0
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            TestHelperObject.DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = TestHelperObject.DEFAULT_PAGE_TO_FETCH,
            movies = emptyList(),
            totalPages = 1,
            totalResults = 0
        )
        val actualResult = repo.getMoviesByQuery(DEFAULT_QUERY, TestHelperObject.DEFAULT_PAGE_TO_FETCH)

        verify(service, times(1)).fetchMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = TestHelperObject.LOCALE_AS_STRING,
            query = DEFAULT_QUERY,
            order = dummyRuntimeArguments.order,
            page = TestHelperObject.DEFAULT_PAGE_TO_FETCH
        )

        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getMoviesByQuery_singleMovie_posterIsPresent() = runTest {
        whenever(
            service.fetchMovies(
                apiKey = ArgumentMatchers.anyString(),
                locale = any(),
                query = any(),
                order = ArgumentMatchers.anyString(),
                page = ArgumentMatchers.anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = TestHelperObject.DEFAULT_PAGE_TO_FETCH,
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
            TestHelperObject.DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = TestHelperObject.DEFAULT_PAGE_TO_FETCH,
            movies = listOf(
                MovieShortInfo(
                    id = 1,
                    title = "Title",
                    posterUrl = "${TestHelperObject.SOME_SECURE_BASE_URL}original/posterpath.jpg",
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
        val actualResult = repo.getMoviesByQuery(DEFAULT_QUERY, TestHelperObject.DEFAULT_PAGE_TO_FETCH)

        verify(service, times(1)).fetchMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = TestHelperObject.LOCALE_AS_STRING,
            query = DEFAULT_QUERY,
            order = dummyRuntimeArguments.order,
            page = TestHelperObject.DEFAULT_PAGE_TO_FETCH
        )

        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getMoviesByQuery_singleMovie_posterIsAbsent() = runTest {
        whenever(
            service.fetchMovies(
                apiKey = ArgumentMatchers.anyString(),
                locale = any(),
                query = any(),
                order = ArgumentMatchers.anyString(),
                page = ArgumentMatchers.anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = TestHelperObject.DEFAULT_PAGE_TO_FETCH,
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
            TestHelperObject.DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = TestHelperObject.DEFAULT_PAGE_TO_FETCH,
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
        val actualResult = repo.getMoviesByQuery(DEFAULT_QUERY, TestHelperObject.DEFAULT_PAGE_TO_FETCH)

        verify(service, times(1)).fetchMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = TestHelperObject.LOCALE_AS_STRING,
            query = DEFAULT_QUERY,
            order = dummyRuntimeArguments.order,
            page = TestHelperObject.DEFAULT_PAGE_TO_FETCH
        )

        Assertions.assertEquals(expectedResult, actualResult)
    }


    @Test
    fun getMoviesByQuery_severalMovies_mixed() = runTest {
        whenever(
            service.fetchMovies(
                apiKey = ArgumentMatchers.anyString(),
                locale = any(),
                query = any(),
                order = ArgumentMatchers.anyString(),
                page = ArgumentMatchers.anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = TestHelperObject.DEFAULT_PAGE_TO_FETCH,
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
            TestHelperObject.DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = TestHelperObject.DEFAULT_PAGE_TO_FETCH,
            movies = listOf(
                MovieShortInfo(
                    id = 1,
                    title = "Title",
                    posterUrl = "${TestHelperObject.SOME_SECURE_BASE_URL}original/posterpath.jpg",
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
        val actualResult = repo.getMoviesByQuery(DEFAULT_QUERY, TestHelperObject.DEFAULT_PAGE_TO_FETCH)

        verify(service, times(1)).fetchMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = TestHelperObject.LOCALE_AS_STRING,
            query = DEFAULT_QUERY,
            order = dummyRuntimeArguments.order,
            page = TestHelperObject.DEFAULT_PAGE_TO_FETCH
        )

        Assertions.assertEquals(expectedResult, actualResult)
    }

}