package com.flashbuzz.app.ui.player

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayerJoinScreen(
    onJoin: (String, String) -> Unit // Code, Nickname
) {
    var roomCode by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Join FlashBuzz", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = roomCode,
            onValueChange = { if (it.length <= 6) roomCode = it.uppercase() },
            label = { Text("6-Character Room Code") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Your Nickname") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onJoin(roomCode, nickname) },
            modifier = Modifier.fillMaxWidth(),
            enabled = roomCode.length == 6 && nickname.isNotBlank()
        ) {
            Text("JOIN GAME")
        }
    }
}
