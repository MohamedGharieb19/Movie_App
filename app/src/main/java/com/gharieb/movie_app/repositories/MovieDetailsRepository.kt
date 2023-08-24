package com.gharieb.movie_app.repositories

import android.util.Log
import com.gharieb.movie_app.api.ApiService
import com.gharieb.movie_app.data.getMovieById.MovieById
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val api: ApiService,
    private val apiKey: String
) {
    suspend fun getMovieById(movieId: Int): MovieById?{

        val response = api.getMovieById(movieId,apiKey)
        if (response.isSuccessful){
            return response.body()
            Log.e("test app Movie by id", response.body().toString())
        }
        return null
    }

}