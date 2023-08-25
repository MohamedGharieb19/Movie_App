package com.gharieb.movie_app.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharieb.movie_app.data.trendingMovies.Movie
import com.gharieb.movie_app.data.trendingTv.Tv
import com.gharieb.movie_app.repositories.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {
    private val _searchedItemList = MutableStateFlow(emptyList<Movie>())
    val searchedItemList: StateFlow<List<Movie>> = _searchedItemList

    fun getSearchedItem(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchRepository.getTSearchedItems(query)
            response.collect{ _searchedItemList.value = it }
        }
    }
}