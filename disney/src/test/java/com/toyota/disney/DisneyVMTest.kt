package com.toyota.disney

import app.cash.turbine.test
import com.toyota.disney.common.ResultType
import com.toyota.disney.presentation.AppState
import com.toyota.disney.presentation.DisneyEvent
import com.toyota.disney.presentation.DisneyVM
import com.toyota.disney.respository.DisneyAPIRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DisneyVMTest {
    private lateinit var viewModel: DisneyVM
    private lateinit var repository: DisneyAPIRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
//        repository = FakeSuccessDisneyAPIRepository()
        repository = mockk()
        viewModel = DisneyVM(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GetCharacter success should update uiState with data`() = runTest {
        //Arrange
        //Act
        viewModel.onEvent(DisneyEvent.GetCharacter)
        // Assert
        viewModel.uiState.collect { state ->
            Assert.assertEquals(
                FakeConstants.mockCharacters.data.first().name,
                state.data?.data?.first()?.name
            )
        }
    }

    @Test
    fun `onEvent GetCharacter with success updates uiState with data`() = runTest {
        // Arrange
        val mockCharacters = FakeConstants.mockCharacters
        coEvery { repository.getCharacters() } returns flowOf(ResultType.Success(mockCharacters))
        val stateUpdates = mutableListOf<AppState.UiState>()
        val job = launch { viewModel.uiState.toList(stateUpdates) }

        // Act
        viewModel.onEvent(DisneyEvent.GetCharacter)

        // Assert
        Assert.assertTrue(stateUpdates.isNotEmpty())
        Assert.assertEquals(mockCharacters, stateUpdates.last().data)
        Assert.assertEquals(null, stateUpdates.last().error)
        job.cancel()
    }

    @Test
    fun `onEvent GetCharacter with error updates uiState with error message`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        coEvery { repository.getCharacters() } returns flowOf(ResultType.Error(errorMessage))
        val stateUpdates = mutableListOf<AppState.UiState>()
        val job = launch { viewModel.uiState.toList(stateUpdates) }

        // Act
        viewModel.onEvent(DisneyEvent.GetCharacter)

        // Assert
        Assert.assertTrue(stateUpdates.isNotEmpty())
        Assert.assertEquals(errorMessage, stateUpdates.last().error)
        Assert.assertEquals(null, stateUpdates.last().data)
        job.cancel()
    }
}