package com.gharieb.movie_app.data.trendingPeople

import com.google.gson.annotations.SerializedName

data class TrendingPeople(
    val page: Int,
    @SerializedName("results")
    val trendingPeople: List<People>,
)