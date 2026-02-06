package ci.nsu.moble.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ci.nsu.moble.main.ui.theme.PracticeTheme

// Хранение цветов по названиям
object ColorsPalette {
    val colors: Map<String, Color> = mapOf(
        "Red" to Color(0xFFFF0000),
        "Green" to Color(0xFF00FF00),
        "Blue" to Color(0xFF0000FF),
        "Cyan" to Color(0xFF00FFFF),
        "Magenta" to Color(0xFFFF00FF),
        "Yellow" to Color(0xFFFFFF00),
        "Black" to Color(0xFF000000),
        "White" to Color(0xFFFFFFFF),
        "Orange" to Color(0xFFFFA500),
        "Purple" to Color(0xFF800080)
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                ScaffoldShortContent()
            }
        }
    }
}

@Composable
fun ScaffoldShortContent() {
    androidx.compose.material3.Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        GreetingWithPalette(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun GreetingWithPalette(modifier: Modifier = Modifier) {
    var inputColor by remember { mutableStateOf("") }
    var matchedColor by remember { mutableStateOf<Color?>(null) }
    var currentBackgroundColor by remember { mutableStateOf(Color.White) }
    var buttonColor by remember { mutableStateOf(Color.Gray) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Введите название цвета:")

        Spacer(modifier = Modifier.height(8.dp))

        // Поле для ввода текста
        TextField(
            value = inputColor,
            onValueChange = { inputColor = it },
            placeholder = { Text(text = "Например, Red, Green") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Кнопка "Применить цвет" для изменения фона
        Button(
            onClick = {
                val key = inputColor.trim()
                val found = ColorsPalette.colors[key]
                if (found != null) {
                    buttonColor = found
                } else {
                    Log.d("ColorSearch", "Такого цвета нет :(")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier
                .padding(top = 4.dp)
        ) {
            Text(text = "Применить цвет")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Область, которая меняет цвет фона
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(currentBackgroundColor)
                .padding(8.dp)
        ) {
            Column {
                Text(text = "Доступные палитры:")
                Spacer(modifier = Modifier.height(8.dp))
                PaletteList(
                    palette = ColorsPalette.colors.toList(),
                    onPick = { name, color ->
                        inputColor = name
                        matchedColor = color
                    }
                )
            }
        }
    }
}

@Composable
fun PaletteList(
    palette: List<Pair<String, Color>>,
    onPick: (String, Color) -> Unit
) {
    LazyColumn {
        items(palette) { (name, color) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onPick(name, color) },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    BoxColor(color = color)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = name)
                }
            }
        }
    }
}

@Composable
fun BoxColor(color: Color) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(color = color)
    )
}