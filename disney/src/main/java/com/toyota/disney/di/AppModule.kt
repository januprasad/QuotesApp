package com.toyota.disney.di

import com.toyota.disney.respository.DisneyAPIRepository
import com.toyota.disney.respository.DisneyAPIRepositoryImpl
import com.toyota.disney.retrofit.DisneyAPI
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val API = "https://api.disneyapi.dev"

    @Provides
    fun providesDisneyApi(): DisneyAPI {
        val client = OkHttpClient.Builder().build()
        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(
                DisneyAPI::class.java
            )
    }


    @Provides
    fun bindRepo(api: DisneyAPI) : DisneyAPIRepository =
        DisneyAPIRepositoryImpl(api)
}
