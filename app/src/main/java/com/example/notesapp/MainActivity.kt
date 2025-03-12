package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.model.NoteDatabase
import com.example.notesapp.model.NoteRepository
import com.example.notesapp.view.screens.NotesNavigationGraph
import com.example.notesapp.view.theme.NotesAppTheme
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = NoteDatabase.getDatabase(applicationContext)
        val repository = NoteRepository(database.noteDao())
        val viewModelFactory = NoteViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]

        setContent {
            NotesAppTheme {
                NotesNavigationGraph(noteViewModel = viewModel)
            }
        }
    }
}