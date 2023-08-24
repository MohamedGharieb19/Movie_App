package com.gharieb.movie_app.constants

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiKey {
    companion object {
        const val API_KEY = "d0e3a52ecc0f7ee4d377e3ce8dbe5c31"
    }
}