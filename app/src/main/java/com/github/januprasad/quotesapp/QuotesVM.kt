package com.github.januprasad.quotesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.januprasad.quotesapp.common.NetworkResult
import com.github.januprasad.quotesapp.model.Quote
import com.github.januprasad.quotesapp.repo.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyQuotesVM
    @Inject
    constructor(
        val repository: QuoteRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AppState.UiState())
        val uiState: StateFlow<AppState.UiState> get() = _uiState.asStateFlow()

        fun events(event: Events) {
            when (event) {
                is Events.RandomQuote -> {
                    repository
                        .randomQuote()
                        .onEach { result ->
                            when (result) {
                                is NetworkResult.Loading -> {
                                    val pullToRefresh = event.isPullToRefresh
                                    _uiState.update {
                                        AppState.UiState(
                                            loadingType =
                                            when (pullToRefresh) {
                                                true -> LoadingType.PullToRefreshLoading
                                                false -> LoadingType.CircularLoading
                                            },
                                        )
                                    }
                                }

                                is NetworkResult.Error -> {
                                    _uiState.update { AppState.UiState(error = result.message) }
                                }

                                is NetworkResult.Success -> {
                                    _uiState.update {
                                        AppState.UiState(
                                            data = result.data,
                                        )
                                    }
                                }
                            }
                        }.launchIn(viewModelScope)
                }
            }
        }
    }

sealed class Events {
    data class RandomQuote(
        val isPullToRefresh: Boolean = false,
    ) : Events()
}

object AppState {
    data class UiState(
        val loadingType: LoadingType = LoadingType.CircularLoading,
        val error: String? = null,
        val data: Quote? = null,
    ) {
        val loaded: Boolean = data != null
    }
}

sealed class LoadingType {
    object CircularLoading : LoadingType()

    object PullToRefreshLoading : LoadingType()
}
