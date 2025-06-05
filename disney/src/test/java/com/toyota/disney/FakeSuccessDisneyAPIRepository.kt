package com.toyota.disney

import com.toyota.disney.common.ResultType
import com.toyota.disney.repo.model.Character
import com.toyota.disney.repo.model.DisneyCharResponse
import com.toyota.disney.respository.DisneyAPIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSuccessDisneyAPIRepository : DisneyAPIRepository {
    override fun getCharacters(): Flow<ResultType> = flow {
        emit(ResultType.Success(FakeConstants.mockCharacters))
    }
}

object FakeConstants {
    val mockCharacters = DisneyCharResponse(
        data = listOf(
            Character(
                __v = 1,
                _id = 101,
                allies = listOf("Goofy", "Pluto"),
                createdAt = "2022-01-01T12:00:00Z",
                enemies = listOf("Pete"),
                films = listOf("Fantasia", "Mickey's Christmas Carol"),
                imageUrl = "https://example.com/mickey.jpg",
                name = "Mickey Mouse",
                parkAttractions = listOf("Mickey's PhilharMagic"),
                shortFilms = listOf("Steamboat Willie"),
                sourceUrl = "https://disney.fandom.com/wiki/Mickey_Mouse",
                tvShows = listOf("Mickey Mouse Clubhouse"),
                updatedAt = "2023-01-01T12:00:00Z",
                url = "https://api.disneyapi.dev/characters/101",
                videoGames = listOf("Kingdom Hearts", "Disney Infinity")
            )
        ),
        info = null,
    )
}
