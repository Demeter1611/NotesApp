package com.example.notesapp.model
import androidx.room.*
import com.example.notesapp.view.utils.AnnotatedStringEntity

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: AnnotatedStringEntity,
    @ColumnInfo(name = "content") val content: AnnotatedStringEntity
)
