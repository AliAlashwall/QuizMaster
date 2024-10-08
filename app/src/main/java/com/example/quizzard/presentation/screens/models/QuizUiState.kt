package com.example.quizzard.presentation.screens.models

data class QuizUiState(
    var userName : String = "",
    var clicked : Boolean = false,
    var score : Int = 0,
    var counter : Int = 0,
    var correctAnswer : String = "",
    var question: String = "",
    var listOfAnswer : List<String> = listOf(""),
    var endQuiz : Boolean = false,
    var category : Subject = Subject.Empty,
    var questionListSize : Int = 10,
    var itemIndexed : Int = 0
)
