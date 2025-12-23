package com.flashbuzz.app.firebase

import com.flashbuzz.app.models.GameState
import com.flashbuzz.app.models.Room
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseService {
    private val db = FirebaseFirestore.getInstance()

    suspend fun createRoom(hostId: String): String {
        val code = generateRoomCode()
        val room = Room(
            code = code,
            hostId = hostId,
            state = GameState.LOBBY
        )
        db.collection("rooms").document(code).set(room).await()
        return code
    }

    private fun generateRoomCode(): String {
        return UUID.randomUUID().toString().substring(0, 6).uppercase()
    }

    fun observeRoom(code: String, onUpdate: (Room?) -> Unit) {
        db.collection("rooms").document(code)
            .addSnapshotListener { snapshot, _ ->
                onUpdate(snapshot?.toObject(Room::class.java))
            }
    }

    suspend fun updateGameState(code: String, newState: GameState) {
        db.collection("rooms").document(code)
            .update("state", newState).await()
    }

    suspend fun buzzIn(code: String, playerId: String) {
        val roomRef = db.collection("rooms").document(code)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(roomRef)
            val queue = snapshot.get("buzzerQueue") as? List<String> ?: emptyList()
            if (!queue.contains(playerId)) {
                transaction.update(roomRef, "buzzerQueue", queue + playerId)
            }
        }.await()
    }

    suspend fun verifyAnswer(code: String, playerId: String, isCorrect: Boolean, points: Int) {
        val roomRef = db.collection("rooms").document(code)
        val playerRef = db.collection("players").document(playerId)

        db.runTransaction { transaction ->
            if (isCorrect) {
                // Award points and clear queue
                val currentPlayer = transaction.get(playerRef)
                val currentScore = currentPlayer.getLong("score") ?: 0
                transaction.update(playerRef, "score", currentScore + points)
                transaction.update(roomRef, "buzzerQueue", emptyList())
                transaction.update(roomRef, "state", GameState.QUESTION_LOCKED)
            } else {
                // Remove from queue
                val snapshot = transaction.get(roomRef)
                val queue = (snapshot.get("buzzerQueue") as? List<String> ?: emptyList()).toMutableList()
                queue.remove(playerId)
                transaction.update(roomRef, "buzzerQueue", queue)
            }
        }.await()
    }
}
