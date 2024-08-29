package com.example.quizzard.presentation.screens.quizScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizzard.R
import com.example.quizzard.domain.model.Question
import com.example.quizzard.presentation.component.LoadingScreen
import com.example.quizzard.presentation.screens.QuizUiState
import com.example.quizzard.presentation.screens.QuizViewModel
import com.example.quizzard.presentation.screens.quizScreen.component.AnswerItem
import com.example.quizzard.presentation.screens.quizScreen.component.HomeTopAppBar
import com.example.quizzard.presentation.screens.quizScreen.component.QuestionCard

@Composable
fun QuizScreen(
    quizViewModel: QuizViewModel, navToFinalScreen: () -> Unit, navBack: () -> Unit
) {
    when (val quizUiState = quizViewModel.quizUiState) {
        is QuizUiState.Success -> {
            QuizScreenContent(
                quizUiState.question.results, quizViewModel, navToFinalScreen
            ) {
                quizViewModel.backToHome()
                navBack()
            }
        }

        is QuizUiState.Loading -> LoadingScreen()
        is QuizUiState.Error -> {}
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuizScreenContent(
    questionList: List<Question>,
    quizViewModel: QuizViewModel,
    navToFinalScreen: () -> Unit,
    navBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        quizViewModel.getQuestionDetails(questionList)
    }
    val gameUiState by quizViewModel.gameUiState.collectAsState()
    val shuffledListAnswer =
        remember(gameUiState.listOfAnswer) { gameUiState.listOfAnswer.shuffled() }

    Log.v("MainActivity", gameUiState.correctAnswer)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTopAppBar(
            "${gameUiState.category}",
            gameUiState.score,
            gameUiState.questionListSize,
            navBack
        )

        QuestionCard(gameUiState.question, gameUiState.counter, gameUiState.questionListSize)

        Spacer(modifier = Modifier.weight(1f)/*height(25.dp)*/)

        AnswersList(
            listAnswer = shuffledListAnswer,
            correctAnswer = gameUiState.correctAnswer,
            solved = gameUiState.clicked,
            onItemClicked = { quizViewModel.onItemClicked(it) },
            incScore = { quizViewModel.incScore() },
            currentSelectedItem = quizViewModel.gameUiState.value.itemIndexed
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { quizViewModel.onNextClick(navToFinalScreen) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),

            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.next_question),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    BackHandler {
        // use only upper back button to back
    }
}

@Composable
fun AnswersList(
    listAnswer: List<String>,
    correctAnswer: String,
    solved: Boolean,
    currentSelectedItem: Int,
    onItemClicked: (Int) -> Unit,
    incScore: () -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(listAnswer) { index, possibleAnswer ->
            AnswerItem(
                listAnswer = listAnswer,
                correctAnswer = correctAnswer,
                selectedAnswer = possibleAnswer,
                solved = solved,
                itemIndex = index,
                currentSelectedItem = currentSelectedItem,
                onItemClicked = { onItemClicked(index) },
                incScore = incScore
            )
        }
    }
}


