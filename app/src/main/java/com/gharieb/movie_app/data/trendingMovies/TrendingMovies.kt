package com.gharieb.movie_app.data.trendingMovies

import com.google.gson.annotations.SerializedName

data class TrendingMovies(
    val page: Int,
    @SerializedName("results")
    val trendingMovie: List<Movie>,
)