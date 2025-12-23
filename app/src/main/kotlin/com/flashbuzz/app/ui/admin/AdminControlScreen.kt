package com.flashbuzz.app.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flashbuzz.app.models.Question

@Composable
fun AdminControlScreen(
    currentQuestion: Question?,
    buzzerQueue: List<String>,
    onNextQuestion: () -> Unit,
    onOpenBuzzer: () -> Unit,
    onVerifyAnswer: (String, Boolean) -> Unit // PlayerID, isCorrect
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Admin Control", style = MaterialTheme.typography.headlineSmall)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (currentQuestion != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Current Question", style = MaterialTheme.typography.labelSmall)
                    Text(currentQuestion.text, style = MaterialTheme.typography.titleLarge)
                    Text("Points: ${currentQuestion.points}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = onOpenBuzzer) {
                Text("OPEN BUZZER")
            }
            Button(onClick = onNextQuestion) {
                Text("NEXT QUESTION")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Buzzer Queue", style = MaterialTheme.typography.titleMedium)
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(buzzerQueue) { index, playerId ->
                ListItem(
                    headlineContent = { Text(playerId) },
                    overlineContent = { Text("Position: #${index + 1}") },
                    trailingContent = {
                        Row {
                            TextButton(onClick = { onVerifyAnswer(playerId, true) }) {
                                Text("Correct", color = MaterialTheme.colorScheme.primary)
                            }
                            TextButton(onClick = { onVerifyAnswer(playerId, false) }) {
                                Text("Wrong", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                )
            }
        }
    }
}
