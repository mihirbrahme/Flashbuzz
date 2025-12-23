package com.flashbuzz.app.network

import com.flashbuzz.app.models.Question
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizImporter {
    private val client = OkHttpClient()

    suspend fun importFromCsv(url: String): List<Question> = withContext(Dispatchers.IO) {
        // Handle Google Sheets "Export as CSV" link format if needed
        val downloadUrl = if (url.contains("docs.google.com/spreadsheets") && !url.contains("export?format=csv")) {
            url.substringBefore("/edit") + "/export?format=csv"
        } else {
            url
        }

        val request = Request.Builder().url(downloadUrl).build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw Exception("Failed to fetch CSV: ${response.code}")

        val csvData = response.body?.string() ?: ""
        parseCsv(csvData)
    }

    private fun parseCsv(data: String): List<Question> {
        val questions = mutableListOf<Question>()
        val lines = data.lines()
        
        // Skip header
        for (i in 1 until lines.size) {
            val line = lines[i]
            if (line.isBlank()) continue
            
            val columns = line.split(",")
            if (columns.size >= 2) {
                val text = columns[0].trim().removeSurrounding("\"")
                val points = columns[1].trim().removeSurrounding("\"").toIntOrNull() ?: 10
                val timeLimit = if (columns.size >= 3) {
                    columns[2].trim().removeSurrounding("\"").toIntOrNull() ?: 30
                } else 30
                
                questions.add(Question(text, points, timeLimit))
            }
        }
        return questions
    }
}
