package com.example.latestmoviesapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.latestmoviesapp.data.movies.repos.LatestMoviesRepo
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val moviesRepo: LatestMoviesRepo
) : ViewModel() {

    val movies: Flow<PagingData<MovieShortInfo>> = Pager(PagingConfig(pageSize = 20)) {
        MovieSource(moviesRepo)
    }.flow.cachedIn(viewModelScope)

}