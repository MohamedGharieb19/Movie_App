package com.gharieb.movie_app.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharieb.movie_app.data.categories.Categories
import com.gharieb.movie_app.data.trendingMovies.Movie
import com.gharieb.movie_app.repositories.MyListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val myListRepository: MyListRepository
): ViewModel() {
    private val _categoriesList = MutableStateFlow(emptyList<Categories>())
    val categoriesList: StateFlow<List<Categories>> = _categoriesList

    private val _movieByCategoryList = MutableStateFlow(emptyList<Movie>())
    val movieByCategoryList: StateFlow<List<Movie>> = _movieByCategoryList

    fun getCategoriesOfMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = myListRepository.getCategoriesOfMovies()
            response.collect{ _categoriesList.value = it }
        }
    }

    fun getMoviesByCategory(categoryNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = myListRepository.getMoviesByCategory(categoryNumber)
            response.collect{ _movieByCategoryList.value = it }
        }
    }
}