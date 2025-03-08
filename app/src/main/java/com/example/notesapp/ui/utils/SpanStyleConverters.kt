package com.example.notesapp.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

fun convertStringToColor(colorString: String) : Color {
    return when(colorString.lowercase()){
        "black" -> Color.Black
        "red" -> Color.Red
        "blue" -> Color.Blue
        "green" -> Color.Green
        "yellow" -> Color.Yellow
        "magenta" -> Color.Magenta
        "Cyan" -> Color.Cyan
        else -> Color.Black
    }
}

fun convertStringToTextStyle(styleString: String) : SpanStyle {
    return when(styleString.lowercase()){
        "bold" -> SpanStyle(fontWeight = FontWeight.Bold)
        "italic" -> SpanStyle(fontStyle = FontStyle.Italic)
        "underline" -> SpanStyle(textDecoration = TextDecoration.Underline)
        else -> SpanStyle()
    }
}