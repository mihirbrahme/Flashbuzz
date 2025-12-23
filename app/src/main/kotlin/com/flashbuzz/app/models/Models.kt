package com.flashbuzz.app.models

data class Quiz(
    val id: String = "",
    val name: String = "",
    val questions: List<Question> = emptyList()
)

data class Question(
    val text: String = "",
    val points: Int = 0,
    val timeLimit: Int = 30 // seconds
)

data class Player(
    val id: String = "",
    val nickname: String = "",
    val score: Int = 0,
    val isOnline: Boolean = true
)

enum class GameState {
    LOBBY,
    QUESTION_LOCKED,
    QUESTION_OPEN,
    VALIDATION,
    END
}

data class Room(
    val code: String = "",
    val hostId: String = "",
    val currentQuiz: Quiz? = null,
    val currentQuestionIndex: Int = -1,
    val state: GameState = GameState.LOBBY,
    val buzzerQueue: List<String> = emptyList() // List of player IDs
)
