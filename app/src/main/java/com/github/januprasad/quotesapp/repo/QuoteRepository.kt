package com.github.januprasad.quotesapp.repo

import com.github.januprasad.quotesapp.common.NetworkResult
import com.github.januprasad.quotesapp.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun randomQuote(): Flow<NetworkResult<Quote>>
}
