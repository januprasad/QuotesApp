package com.toyota.disney

import com.toyota.disney.common.ResultType
import com.toyota.disney.respository.DisneyAPIRepositoryImpl
import com.toyota.disney.retrofit.DisneyAPI
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetCharactersTest {
    private val api: DisneyAPI = mockk()
    private val testDispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `getCharacters success with data emits Success result`() = runTest {
        // Arrange
        val mockCharacters = FakeConstants.mockCharacters
        val successResponse = Response.success(mockCharacters)
        coEvery { api.getCharacters() } returns successResponse
        val disneyAPIRepositoryImpl = DisneyAPIRepositoryImpl(
            api = api
        )

        // Act
        val results = disneyAPIRepositoryImpl.getCharacters().flowOn(testDispatcher).toList()

        // Assert
        Assert.assertEquals(1, results.size)
        Assert.assertTrue(results[0] is ResultType.Success)
        Assert.assertEquals(mockCharacters, (results[0] as ResultType.Success).response)
    }

    /* @Test
     fun `getCharacters success with null body emits Error result`() = runTest {
         // Arrange
         val successResponse = Response.success<List<Character>>(null)
         coEvery { api.getCharacters() } returns successResponse

         // Act
         val results = getCharacters().flowOn(testDispatcher).toList()

         // Assert
         assertEquals(1, results.size)
         assertTrue(results[0] is ResultType.Error)
         assertEquals("", (results[0] as ResultType.Error).message)
     }*/

    /* @Test
     fun `getCharacters failure emits Error result`() = runTest {
         // Arrange
         val errorMessage = "Network error"
         val errorResponse = Response.error<List<Character>>(400, mockk(relaxed = true) {
             coEvery { message() } returns errorMessage
         })
         coEvery { api.getCharacters() } returns errorResponse

         // Act
         val results = getCharacters().flowOn(testDispatcher).toList()

         // Assert
         assertEquals(1, results.size)
         assertTrue(results[0] is ResultType.Error)
         assertEquals(errorMessage, (results[0] as ResultType.Error).message)
     }*/
}
