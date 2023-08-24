package com.gharieb.movie_app.data.trendingTv

import com.google.gson.annotations.SerializedName

data class TrendingTv(
    val page: Int,
    @SerializedName("results")
    val trendingTv: List<Tv>,

    )