package com.gharieb.movie_app.api

import com.gharieb.movie_app.data.categories.CategoriesResponse
import com.gharieb.movie_app.data.getMovieById.MovieById
import com.gharieb.movie_app.data.trendingMovies.Movie
import com.gharieb.movie_app.data.trendingMovies.TrendingMovies
import com.gharieb.movie_app.data.trendingPeople.TrendingPeople
import com.gharieb.movie_app.data.trendingTv.TrendingTv
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_key") ApiKey: String
    ): Response<TrendingMovies>

    @GET("search/movie")
    suspend fun getSearchedItem(
        @Query("api_key") ApiKey: String,
        @Query("query") searchedItem: String
    ): Response<TrendingMovies>

    @GET("genre/movie/list")
    suspend fun getMoviesCategories(
        @Query("api_key") ApiKey: String
    ): Response<CategoriesResponse>

    @GET("discover/movie")
    suspend fun getMovieByCategory(
        @Query("api_key") ApiKey: String,
        @Query ("with_genres") categoryNumber: Int
    ): Response<TrendingMovies>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") MovieId: Int,
        @Query("api_key") ApiKey: String
    ): Response<MovieById>
}