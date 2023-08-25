package com.gharieb.movie_app.repositories

import com.gharieb.movie_app.api.ApiService
import com.gharieb.movie_app.data.categories.Categories
import com.gharieb.movie_app.data.trendingMovies.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MyListRepository @Inject constructor(
    private val api: ApiService,
    private val apiKey: String
) {

    suspend fun getCategoriesOfMovies() : StateFlow<List<Categories>> {
        val categoriesList = MutableStateFlow(emptyList<Categories>())

        val response = api.getMoviesCategories(apiKey)
        if (response.isSuccessful){
            val body = response.body()?.categoriesList
            categoriesList.value = body!!
        }
        return categoriesList
    }

    suspend fun getMoviesByCategory(categoryNumber: Int) : StateFlow<List<Movie>> {
        val movieByCategoryList = MutableStateFlow(emptyList<Movie>())

        val response = api.getMovieByCategory(apiKey,categoryNumber)
        if (response.isSuccessful){
            val body = response.body()?.trendingMovie
            movieByCategoryList.value = body!!
        }
        return movieByCategoryList
    }
}