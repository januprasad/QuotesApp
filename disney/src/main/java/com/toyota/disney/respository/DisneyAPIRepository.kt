package com.toyota.disney.respository

import com.toyota.disney.common.ResultType
import kotlinx.coroutines.flow.Flow

interface DisneyAPIRepository {
    fun getCharacters() : Flow<ResultType>
}