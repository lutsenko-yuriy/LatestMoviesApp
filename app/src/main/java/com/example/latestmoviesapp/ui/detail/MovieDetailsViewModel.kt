package com.example.latestmoviesapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.latestmoviesapp.data.movies.repos.MovieDetailedInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepo: MovieDetailedInfoRepo
) : ViewModel() {

    private val _movieDetails = MutableSharedFlow<MovieDetailsScreenState>()
    val movieDetails: Flow<MovieDetailsScreenState> = _movieDetails

    fun init(movieId: Int) {
        viewModelScope.launch {
            _movieDetails.emit(MovieDetailsScreenState.Loading)
            _movieDetails.emit(
                try {
                    MovieDetailsScreenState.Success(movieDetailsRepo.getMovieDetailedInfo(movieId))
                } catch (e: Exception) {
                    MovieDetailsScreenState.Error
                }
            )
        }
    }

}