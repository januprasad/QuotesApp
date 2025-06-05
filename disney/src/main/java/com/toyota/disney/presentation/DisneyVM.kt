package com.toyota.disney.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toyota.disney.common.ResultType
import com.toyota.disney.repo.model.DisneyCharResponse
import com.toyota.disney.respository.DisneyAPIRepository
import com.toyota.disney.respository.DisneyAPIRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DisneyVM @Inject constructor(
    private val disneyAPIRepository: DisneyAPIRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppState.UiState())
    val uiState: StateFlow<AppState.UiState> get() = _uiState.asStateFlow()

    fun onEvent(event: DisneyEvent) {
        when (event) {
            DisneyEvent.GetCharacter -> {
                disneyAPIRepository.getCharacters().onEach { result ->
                    when (result) {
                        is ResultType.Error -> {
                            AppState.UiState(error = result.message)
                        }

                        is ResultType.Success -> {
                            _uiState.update {
                                AppState.UiState(data = result.response)
                            }
                        }
                    }

                }.launchIn(viewModelScope)
            }
        }
    }

}

sealed interface DisneyEvent {
    object GetCharacter : DisneyEvent
}

object AppState {
    data class UiState(
        val error: String? = null,
        val data: DisneyCharResponse? = null,
    )
}
