package com.example.quizzard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizzard.presentation.screens.QuizViewModel
import com.example.quizzard.presentation.screens.models.QuizUiState
import com.example.quizzard.presentation.screens.categorySelection.CategorySelection
import com.example.quizzard.presentation.screens.finalScreen.FinalScreen
import com.example.quizzard.presentation.screens.quizScreen.QuizScreen
import com.example.quizzard.presentation.screens.loginWithName.LoginWithNameScreen


@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    quizViewModel: QuizViewModel,
    quizUiState: QuizUiState
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Start.name

    ) {
        composable(route = Screens.Start.name) {
            LoginWithNameScreen(quizViewModel) {
                navController.navigate(Screens.CategorySelection.name)
            }
        }
        composable(route = Screens.CategorySelection.name) {
            CategorySelection(quizViewModel) {
                navController.navigate(Screens.Home.name)
            }
        }
        composable(route = Screens.Home.name) {
            QuizScreen(
                quizViewModel = quizViewModel,
                navToFinalScreen = { navController.navigate(Screens.Final.name) },
                navBack = {
                    quizViewModel.backToHome()
                    navController.popBackStack(
                        Screens.CategorySelection.name,
                        false
                    )
                }
            )
        }
        composable(route = Screens.Final.name) {
            FinalScreen(
                userName = quizUiState.userName,
                score = quizUiState.score,
                onTryAgainClicked = { navController.popBackStack(Screens.Start.name, false) },
                listSize = quizUiState.questionListSize,
                resetQuiz = { quizViewModel.resetGame() }
            )
        }
    }
}