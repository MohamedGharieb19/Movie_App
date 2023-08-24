package com.gharieb.movie_app.data.categories

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("genres")
    val categoriesList: List<Categories>
)