package com.github.januprasad.quotesapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.januprasad.quotesapp.theme.Typography
import com.github.januprasad.quotesapp.theme.pales
import com.github.januprasad.quotesapp.theme.textColor

@OptIn(ExperimentalMaterialApi::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun QuotesAppScreen(
    appState: AppState.UiState,
    modifier: Modifier = Modifier,
    onEvent: (Events) -> Unit,
) {
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = !appState.loaded,
            onRefresh = {
                onEvent(Events.RandomQuote(true))
            },
        )
    LaunchedEffect(key1 = true) {
        onEvent(Events.RandomQuote(false))
    }
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (appState.loaded) {
            true ->
                Box(
                    Modifier.fillMaxSize().pullRefresh(pullRefreshState).verticalScroll(rememberScrollState()),
                ) {
                    Card(
                        colors =
                            CardDefaults.cardColors(
                                containerColor = pales.random(),
                            ),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        elevation = CardDefaults.elevatedCardElevation(),
                    ) {
                        Column(
                            Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = appState.data?.quote.orEmpty(),
                                style = Typography.bodyLarge,
                                color = textColor,
                            )
                            Text(
                                text = appState.data?.author.orEmpty(),
                                style = Typography.labelSmall,
                                color = textColor,
                            )
                        }
                    }
                }

            false -> {
                when (appState.loadingType) {
                    LoadingType.CircularLoading -> {
                        CircularProgressIndicator(color = textColor, strokeWidth = 3.dp)
                    }

                    LoadingType.PullToRefreshLoading -> {
                        PullRefreshIndicator(
                            refreshing = true,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter),
                            backgroundColor = pales.random(),
                        )
                    }
                }
            }
        }
    }
}
