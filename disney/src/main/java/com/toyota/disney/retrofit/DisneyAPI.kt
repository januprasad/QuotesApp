package com.toyota.disney.retrofit

import com.toyota.disney.repo.model.DisneyCharResponse
import retrofit2.Response
import retrofit2.http.GET

interface DisneyAPI {
    @GET("/character")
    suspend fun getCharacters(): Response<DisneyCharResponse>
}