package com.example.latestmoviesapp.ui.search.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.latestmoviesapp.data.movies.repos.MoviesByQueryRepo
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.ui.misc.paging.MoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val moviesRepo: MoviesByQueryRepo
) : ViewModel() {

    private val _queryString = MutableStateFlow("")
    val queryString: Flow<String> = _queryString

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val movies: Flow<PagingData<MovieShortInfo>> =
        _queryString
            .filter { query -> query.isNotEmpty() }
            .distinctUntilChanged { old, new -> old == new }
            .debounce(500)
            .flatMapLatest { query ->
                Pager(PagingConfig(pageSize = 20)) {
                    MoviesPagingSource { page -> moviesRepo.getMoviesByQuery(query, page) }
                }.flow
            }.cachedIn(viewModelScope)

    fun submitNewSearchQuery(newQuery: String) {
        _queryString.value = newQuery
    }

}