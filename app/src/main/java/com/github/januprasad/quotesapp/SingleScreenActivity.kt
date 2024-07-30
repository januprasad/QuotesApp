package com.github.januprasad.quotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.januprasad.quotesapp.theme.InterviewprepTheme
import com.github.januprasad.quotesapp.theme.pales
import com.github.januprasad.quotesapp.theme.textColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InterviewprepTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors =
                                TopAppBarDefaults.topAppBarColors(
                                    containerColor = pales.random(),
                                ),
                            title = {
                                Text(text = "Daily Quotes", color = textColor)
                            },
                        )
                    },
                    containerColor = pales.random(),
                ) { innerPadding ->
                    val quotesVM: MyQuotesVM = viewModel()
                    val appState = quotesVM.uiState.collectAsStateWithLifecycle()
                    QuotesAppScreen(
                        appState = appState.value,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        quotesVM.events(it)
                    }
                }
            }
        }
    }
}
