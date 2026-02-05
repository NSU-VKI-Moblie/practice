package ci.nsu.moble.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ci.nsu.moble.main.ui.theme.MobilepracticeTheme

import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val colorPalette = mapOf(
    "red" to Color.Red,
    "blue" to Color.Blue,
    "green" to Color.Green,
    "yellow" to Color.Yellow,
    "magenta" to Color.Magenta,
    "black" to Color.Black
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Content goes under status bar
        setContent {
            MobilepracticeTheme {
                // Makes right padding
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Calling screen
                    ColorSearchScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ColorSearchScreen(modifier: Modifier = Modifier) {
    // Состояние: текст в поле и текущий цвет кнопки
    var searchQuery by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Gray) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Поиск цвета",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Поле ввода названия цвета
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Например: Red") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка, которая меняет цвет
        Button(
            onClick = {
                val foundColor = colorPalette[searchQuery.trim().lowercase()]
                if (foundColor != null) {
                    buttonColor = foundColor
                } else {
                    Log.d("ColorSearch", "Пользовательский цвет \"$searchQuery\" не найден")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Применить цвет")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Дополнительно: Список всей палитры
        Text("Доступные цвета:", style = MaterialTheme.typography.titleMedium)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(colorPalette.toList()) { (name, color) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = name.replaceFirstChar { it.uppercase() })
                }
            }
        }
    }
}