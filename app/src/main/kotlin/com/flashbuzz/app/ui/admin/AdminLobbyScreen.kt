package com.flashbuzz.app.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminLobbyScreen(
    roomCode: String,
    playerCount: Int,
    onStartQuiz: () -> Unit,
    onImportQuiz: (String) -> Unit
) {
    var sheetUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Admin Lobby", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Room Code", style = MaterialTheme.typography.labelLarge)
                Text(
                    roomCode,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Players Joined: $playerCount", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = sheetUrl,
            onValueChange = { sheetUrl = it },
            label = { Text("Google Sheets URL") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = { onImportQuiz(sheetUrl) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Import Quiz")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onStartQuiz,
            modifier = Modifier.fillMaxWidth(),
            enabled = playerCount > 0,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Start Game")
        }
    }
}
