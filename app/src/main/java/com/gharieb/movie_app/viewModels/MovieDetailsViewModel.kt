package com.gharieb.movie_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharieb.movie_app.data.getMovieById.MovieById
import com.gharieb.movie_app.data.trendingMovies.Movie
import com.gharieb.movie_app.repositories.MovieDetailsRepository
import com.gharieb.movie_app.repositories.MyListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel@Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val myListRepository: MyListRepository
): ViewModel() {

    private val _movie = MutableLiveData<MovieById>()
    val movie: LiveData<MovieById>
        get() = _movie

    fun fetchMovieById(movieId: Int) {
        viewModelScope.launch {
            val fetchedMovie = movieDetailsRepository.getMovieById(movieId)
            _movie.value = fetchedMovie!!
        }
    }

}