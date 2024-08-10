package com.example.quizzard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizzard.presentation.navigation.NavGraph
import com.example.quizzard.presentation.theme.QuizMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMasterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val quizViewModel: QuizViewModel = viewModel()
                    val gameUiState by quizViewModel.gameUiState.collectAsState()
                    NavGraph(
                        quizViewModel = quizViewModel,
                        gameUiState = gameUiState
                    )

                }
            }
        }
    }
}
