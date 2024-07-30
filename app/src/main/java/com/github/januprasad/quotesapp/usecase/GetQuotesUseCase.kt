package com.github.januprasad.quotesapp.usecase

import com.github.januprasad.quotesapp.common.NetworkResult
import com.github.januprasad.quotesapp.model.Quote
import com.github.januprasad.quotesapp.repo.QuoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetQuotesUseCase {
    operator fun invoke(): Flow<NetworkResult<Quote>>
}

class GetQuotesUseCaseImpl @Inject constructor(
    private val quoteRepository: QuoteRepository
) : GetQuotesUseCase {
    override fun invoke(): Flow<NetworkResult<Quote>> =
        quoteRepository
            .randomQuote()
}