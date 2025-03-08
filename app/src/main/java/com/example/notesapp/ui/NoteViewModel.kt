package com.example.notesapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import com.example.notesapp.data.Note
import kotlin.random.Random

class NoteViewModel : ViewModel() {
    private val _notesList = mutableListOf<Note>()
    val notesList: List<Note> = _notesList

    private val _currentNote = mutableStateOf<Note?>(null)
    val currentNote : State<Note?> = _currentNote


    init{
        addNote(Note(title = buildAnnotatedString { append("titlu1") }, content = buildAnnotatedString { append("content1") }))
        addNote(Note(title = buildAnnotatedString { append("titlu2") }, content = buildAnnotatedString { append("content2") }))
        addNote(Note(title = buildAnnotatedString { append("titlu3") }, content = buildAnnotatedString { append("content3") }))
        addNote(Note(title = buildAnnotatedString { append("titlu4") }, content = buildAnnotatedString { append("content4") }))
        addNote(Note(title = buildAnnotatedString { append("titlu5") }, content = buildAnnotatedString { append("content5") }))
    }

    fun setCurrentNote(id: Int){
        val index = _notesList.indexOfFirst { it.id == id }
        if(index != -1){
            _currentNote.value = _notesList[index]
        }
    }

    fun addNote(note: Note = Note(id = _notesList.size)){
        note.id = getRandomUniqueID()
        _notesList.add(note)
    }

    fun deleteNote(note: Note){
        _notesList.remove(note)
    }

    fun updateNote(note: Note){
        val index = _notesList.indexOfFirst { it.id == note.id }
        if(index != -1){
            _notesList[index] = note
        }
    }

    private fun getRandomUniqueID(): Int {
        var randomID: Int
        do{
            randomID = Random.nextInt(0, 10000)
            var unique = true
            for(notes in _notesList){
                if(notes.id == randomID){
                    unique = false
                    break
                }
            }
        } while(!unique)
        return randomID
    }
}