package com.gharieb.movie_app.data.trendingTv

import com.google.gson.annotations.SerializedName

data class Tv(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val image: String,
    val vote_average: Double,

)