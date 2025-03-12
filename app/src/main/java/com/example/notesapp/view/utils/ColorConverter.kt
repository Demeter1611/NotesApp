package com.example.notesapp.view.utils

import androidx.compose.ui.graphics.Color

object ColorConverter {
    private val colorMap = mapOf(
        "black" to Color.Black,
        "red" to Color.Red,
        "blue" to Color.Blue,
        "green" to Color.Green,
        "yellow" to Color.Yellow,
        "magenta" to Color.Magenta,
        "cyan" to Color.Cyan,
    )

    fun convertStringToColor(colorString: String) : Color {
        return colorMap[colorString.lowercase()] ?: Color.Black
    }

    fun convertColorToString(color: Color) : String {
        return colorMap.entries.find { it.value == color }?.key ?: ""
    }
}