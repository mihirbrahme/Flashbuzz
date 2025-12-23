package com.flashbuzz.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoleSelectionScreen(
    onSelectAdmin: () -> Unit,
    onSelectPlayer: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("FlashBuzz", style = MaterialTheme.typography.displayLarge)
        Text("Digital Quiz Buzzer", style = MaterialTheme.typography.labelLarge)
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onSelectAdmin,
            modifier = Modifier.fillMaxWidth(0.7f).height(64.dp)
        ) {
            Text("HOST A QUIZ (ADMIN)")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = onSelectPlayer,
            modifier = Modifier.fillMaxWidth(0.7f).height(64.dp)
        ) {
            Text("JOIN A QUIZ (PLAYER)")
        }
    }
}
