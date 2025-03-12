package com.example.notesapp.view.utils

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class AnnotatedStringConverter {
    @TypeConverter
    fun fromAnnotatedString(value: AnnotatedStringEntity): String{
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toAnnotatedString(value: String): AnnotatedStringEntity{
        return decodeFromString(value)
    }
}