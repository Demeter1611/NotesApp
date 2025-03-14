package com.example.notesapp.view.screens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.view.TextComponent
import com.example.notesapp.view.utils.toAnnotatedString


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, noteViewModel: NoteViewModel){
    val noteList: List<Note> = noteViewModel.allNotes.collectAsState(initial = emptyList()).value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                    //Row(modifier = Modifier.weight(1f)){}

                TextComponent(
                    textValue = "Home",
                    colorValue = Color.Black,
                    fontSizeValue = 48.sp,
                    fontWeightValue = FontWeight.Bold,
                    textAlignValue = TextAlign.Center,
                    )
            },
                modifier = Modifier.padding(top = 50.dp, bottom = 25.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    noteViewModel.addNote(AnnotatedString("New Note"), AnnotatedString(""))
                    /*noteViewModel.setCurrentNote(noteList.last().id)
                    navController.navigate(Routes.VIEW_NOTE_SCREEN)*/
                },
                containerColor = Color.White,
            ) {
                TextComponent(
                    textValue = "Add",
                    colorValue = Color.Black,
                    fontSizeValue = 16.sp,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ){ innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)){
            items(items = noteList, key = { it.id }){
                note ->
                TextComponent(
                    textValue = note.title.toAnnotatedString().text,
                    colorValue = Color.Black,
                    fontSizeValue = 28.sp,
                    modifierValue = Modifier.padding(start = 16.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .clickable{
                            noteViewModel.setCurrentNote(note.id)
                            navController.navigate(Routes.VIEW_NOTE_SCREEN)
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(rememberNavController(), viewModel())
}
