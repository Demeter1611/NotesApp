package com.example.notesapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.ui.NoteViewModel

@Composable
fun NotesNavigationGraph(navController: NavHostController = rememberNavController(), noteViewModel: NoteViewModel = viewModel()){
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN){
        composable(Routes.HOME_SCREEN){
            HomeScreen(navController, noteViewModel)
        }
        composable(Routes.VIEW_NOTE_SCREEN){
            ViewNoteScreen(navController, noteViewModel)
        }
    }
}