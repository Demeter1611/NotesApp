package com.example.notesapp.data

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

data class Note(
    var id: Int = -1,
    var title: AnnotatedString = buildAnnotatedString { append("") },
    var content: AnnotatedString = buildAnnotatedString { append("") },
)
