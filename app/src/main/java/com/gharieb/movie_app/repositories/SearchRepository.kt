package com.gharieb.movie_app.repositories

import android.util.Log
import com.gharieb.movie_app.api.ApiService
import com.gharieb.movie_app.data.trendingPeople.People
import com.gharieb.movie_app.data.trendingTv.Tv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: ApiService,
    private val apiKey: String
) {
    suspend fun getTSearchedItems(query: String) : StateFlow<List<Tv>> {
        val searchItemList = MutableStateFlow(emptyList<Tv>())

        val response = api.getSearchedItem(apiKey,query)
        if (response.isSuccessful){
            val body = response.body()?.trendingTv
            searchItemList.value = body!!
        }
        return searchItemList
    }
}