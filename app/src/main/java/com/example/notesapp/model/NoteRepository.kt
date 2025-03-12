package com.example.notesapp.model

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAll()

    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
    suspend fun getNoteById(id: Int): Note = noteDao.getNoteById(id)
}