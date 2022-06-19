package com.example.latestmoviesapp.ui.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.latestmoviesapp.data.movies.repos.LatestMoviesRepo
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import java.lang.Exception

class MovieSource(private val moviesRepo: LatestMoviesRepo) : PagingSource<Int, MovieShortInfo>() {

    override fun getRefreshKey(state: PagingState<Int, MovieShortInfo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieShortInfo> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = moviesRepo.getLatestMovies(nextPage)

            LoadResult.Page(
                data = movieListResponse.movies,
                prevKey = if (nextPage <= 1) null else nextPage - 1,
                nextKey = if (nextPage >= movieListResponse.totalPages) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}