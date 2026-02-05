package ci.nsu.moble.main.ui.main

import android.graphics.Color
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val availableColors = mapOf(
        "Red" to Color.RED,
        "Orange" to Color.parseColor("#FFA500"),
        "Yellow" to Color.YELLOW,
        "Green" to Color.GREEN,
        "Blue" to Color.BLUE,
        "Indigo" to Color.parseColor("#4B0082"),
        "Violet" to Color.parseColor("#EE82EE")
    )
}