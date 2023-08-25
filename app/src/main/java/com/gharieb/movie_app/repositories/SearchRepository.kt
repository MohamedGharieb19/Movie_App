package com.gharieb.movie_app.repositories


import com.gharieb.movie_app.api.ApiService
import com.gharieb.movie_app.data.trendingMovies.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: ApiService,
    private val apiKey: String
) {
    suspend fun getTSearchedItems(query: String) : StateFlow<List<Movie>> {
        val searchItemList = MutableStateFlow(emptyList<Movie>())

        val response = api.getSearchedItem(apiKey,query)
        if (response.isSuccessful){
            val body = response.body()?.trendingMovie
            searchItemList.value = body!!
        }
        return searchItemList
    }
}