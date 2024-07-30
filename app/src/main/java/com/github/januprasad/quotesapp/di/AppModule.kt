package com.github.januprasad.quotesapp.di

import com.github.januprasad.quotesapp.repo.QuoteRepository
import com.github.januprasad.quotesapp.repo.QuotesRepositoryImpl
import com.github.januprasad.quotesapp.retrofit.QuotesAPI
import com.github.januprasad.quotesapp.usecase.GetQuotesUseCase
import com.github.januprasad.quotesapp.usecase.GetQuotesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesQuotesAPI(): QuotesAPI {
        val logging =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        val client =
            OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .build()

        return Retrofit
            .Builder()
            .client(client)
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesAPI::class.java)
    }

    @Provides
    fun providesQuotesRepo(api: QuotesAPI): QuoteRepository = QuotesRepositoryImpl(api)
}
