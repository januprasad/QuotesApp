package com.toyota.disney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.toyota.disney.presentation.DisneyApp
import com.toyota.disney.ui.theme.QuotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisneyApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

