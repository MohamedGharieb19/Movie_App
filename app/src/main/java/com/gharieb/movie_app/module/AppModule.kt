package com.gharieb.movie_app.module

import android.provider.SyncStateContract.Constants
import com.gharieb.movie_app.api.ApiService
import com.gharieb.movie_app.constants.ApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.reflect.jvm.internal.impl.load.java.Constant

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiKey(): String {
        return ApiKey.API_KEY
    }

    @Provides
    @Singleton
    fun provideApi(): ApiService =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

}