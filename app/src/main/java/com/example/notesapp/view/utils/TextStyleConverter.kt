package com.example.notesapp.view.utils

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

object TextStyleConverter {
    private val textStyleMap = mapOf(
        "bold" to SpanStyle(fontWeight = FontWeight.Bold),
        "italic" to SpanStyle(fontStyle = FontStyle.Italic),
        "underline" to SpanStyle(textDecoration = TextDecoration.Underline)
    )

    fun convertStringToTextStyle(styleString: String) : SpanStyle {
        return textStyleMap[styleString.lowercase()] ?: SpanStyle()
    }

    fun convertTextStyleToString(style: SpanStyle) : String {
        return textStyleMap.entries.find { it.value == style }?.key ?: ""
    }
}