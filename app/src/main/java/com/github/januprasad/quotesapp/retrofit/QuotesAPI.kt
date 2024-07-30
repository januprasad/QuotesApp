package com.github.januprasad.quotesapp.retrofit

import com.github.januprasad.quotesapp.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface QuotesAPI {
    @GET("/quotes/random")
    suspend fun getQuotes(): Response<Quote>
}
