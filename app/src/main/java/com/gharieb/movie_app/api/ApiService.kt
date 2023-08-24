package com.gharieb.movie_app.api

import com.gharieb.movie_app.data.trendingMovies.TrendingMovies
import com.gharieb.movie_app.data.trendingPeople.TrendingPeople
import com.gharieb.movie_app.data.trendingTv.TrendingTv
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("trending/tv/week")
    suspend fun getTrendingTv(
        @Query("api_key") ApiKey: String
    ): Response<TrendingTv>

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") ApiKey: String
    ): Response<TrendingMovies>

    @GET("trending/person/week")
    suspend fun getTrendingPeople(
        @Query("api_key") ApiKey: String
    ): Response<TrendingPeople>

    @GET("search/collection")
    suspend fun getSearchedItem(
        @Query("api_key") ApiKey: String,
        @Query("query") searchedItem: String
    ): Response<TrendingTv>

}