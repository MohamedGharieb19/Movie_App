package com.gharieb.movie_app.repositories

import android.util.Log
import com.gharieb.movie_app.api.ApiService
import com.gharieb.movie_app.data.trendingMovies.Movie
import com.gharieb.movie_app.data.trendingPeople.People
import com.gharieb.movie_app.data.trendingTv.Tv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiService,
    private val apiKey: String
) {

    suspend fun getTrendingTv() : StateFlow<List<Tv>>{
        val tvList = MutableStateFlow(emptyList<Tv>())

        val response = api.getTrendingTv(apiKey)
        if (response.isSuccessful){
            val body = response.body()?.trendingTv
            tvList.value = body!!
        }
        return tvList
    }

    suspend fun getTrendingMovies() : StateFlow<List<Movie>>{
        val movieList = MutableStateFlow(emptyList<Movie>())

        val response = api.getTrendingMovies(apiKey)
        if (response.isSuccessful){
            val body = response.body()?.trendingMovie
            movieList.value = body!!
        }
        return movieList
    }

    suspend fun getTrendingPeople() : StateFlow<List<People>>{
        val peopleList = MutableStateFlow(emptyList<People>())

        val response = api.getTrendingPeople(apiKey)
        if (response.isSuccessful){
            val body = response.body()?.trendingPeople
            peopleList.value = body!!
        }
        return peopleList
    }



}