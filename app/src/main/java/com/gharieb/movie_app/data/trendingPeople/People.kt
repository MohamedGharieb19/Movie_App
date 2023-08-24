package com.gharieb.movie_app.data.trendingPeople

import com.google.gson.annotations.SerializedName

data class People(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val media_type: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val image: String
)