package com.example.notesapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.model.NoteRepository
import com.example.notesapp.view.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel(){
    val allNotes: Flow<List<Note>> = repository.allNotes
        .distinctUntilChanged()
    val currentNote = mutableStateOf<Note?>(null)

    fun addNote(title: AnnotatedString, content: AnnotatedString){
        viewModelScope.launch {
            repository.insert(Note(title = title.toEntity(), content = content.toEntity()))
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun setCurrentNote(id: Int){
        viewModelScope.launch {
            currentNote.value = repository.getNoteById(id)
        }
    }
}