package com.flashbuzz.app.ui.player

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flashbuzz.app.models.GameState

@Composable
fun PlayerBuzzerScreen(
    gameState: GameState,
    queuePosition: Int, // 0 if not in queue
    questionText: String,
    points: Int,
    onBuzz: () -> Unit
) {
    val buzzerColor by animateColorAsState(
        targetValue = when {
            queuePosition > 0 -> Color(0xFF4CAF50) // Green (Locked)
            gameState == GameState.QUESTION_OPEN -> Color(0xFFF44336) // Red (Active)
            else -> Color(0xFF9E9E9E) // Grey (Disabled)
        },
        label = "buzzerColor"
    )

    val labelText = when {
        queuePosition > 0 -> "BUZZED!\nPosition #$queuePosition"
        gameState == GameState.QUESTION_OPEN -> "TAP TO\nBUZZ!"
        else -> "WAIT FOR\nHOST..."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Question Info
        Text(
            text = "Question: $questionText",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "$points Points",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.weight(1f))

        // Large Circular Buzzer
        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(CircleShape)
                .background(buzzerColor)
                .clickable(enabled = gameState == GameState.QUESTION_OPEN && queuePosition == 0) {
                    onBuzz()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = labelText,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        
        // Progress Info
        if (queuePosition > 0) {
            Text(
                "Stand by for host verification",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}
