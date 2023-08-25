package com.gharieb.movie_app.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharieb.movie_app.data.trendingMovies.Movie
import com.gharieb.movie_app.data.trendingPeople.People
import com.gharieb.movie_app.data.trendingTv.Tv
import com.gharieb.movie_app.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) :ViewModel() {
    private val _tvList = MutableStateFlow(emptyList<Tv>())
    val tvList: StateFlow<List<Tv>> = _tvList

    private val _movieList = MutableStateFlow(emptyList<Movie>())
    val movieList: StateFlow<List<Movie>> = _movieList

    private val _upComingMovieList = MutableStateFlow(emptyList<Movie>())
    val upComingMovieList: StateFlow<List<Movie>> = _upComingMovieList

    private val _peopleList = MutableStateFlow(emptyList<People>())
    val peopleList: StateFlow<List<People>> = _peopleList

    fun getTrendingTv() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = homeRepository.getTrendingTv()
            response.collect{ _tvList.value = it }
        }
    }

    fun getTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = homeRepository.getTrendingMovies()
            response.collect{ _movieList.value = it }
        }
    }

    fun getUpComingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = homeRepository.getUpComingMovies()
            response.collect{ _upComingMovieList.value = it }
        }
    }

    fun getTrendingPeople() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = homeRepository.getTrendingPeople()
            response.collect{ _peopleList.value = it }
        }
    }


}