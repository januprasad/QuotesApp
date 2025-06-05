package com.toyota.disney.respository

import com.toyota.disney.common.ResultType
import com.toyota.disney.retrofit.DisneyAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DisneyAPIRepositoryImpl @Inject constructor(private val api: DisneyAPI) :
    DisneyAPIRepository {
    override fun getCharacters() =
        flow {
            val response = api.getCharacters()
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    emit(ResultType.Success(result))
                } ?: run {
                    emit(ResultType.Error(message = response.message()))
                }
            } else emit(ResultType.Error(message = response.message()))
        }.flowOn(Dispatchers.IO)
}