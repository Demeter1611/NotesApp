package com.example.notesapp.ui.screens
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.ui.NoteViewModel
import com.example.notesapp.ui.RichTextBottomBar
import com.example.notesapp.ui.TextComponent
import com.example.notesapp.ui.TextFieldComponent
import com.example.notesapp.ui.utils.changeColor
import com.example.notesapp.ui.utils.changeFontSize
import com.example.notesapp.ui.utils.toggleStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNoteScreen(navController : NavHostController, noteViewModel: NoteViewModel){
    val currentNote = noteViewModel.currentNote.value
    if(currentNote == null){
        navController.popBackStack()
        return
    }

    var noteTitle by remember { mutableStateOf(TextFieldValue(annotatedString = currentNote.title,
        selection = TextRange(currentNote.title.text.length)))}

    var noteContent by remember { mutableStateOf(TextFieldValue(annotatedString = currentNote.content,
    selection = TextRange(currentNote.content.text.length)))}

    DisposableEffect(Unit) {
        onDispose {
            val updatedNote = currentNote.copy(title = noteTitle.annotatedString, content = noteContent.annotatedString)
            noteViewModel.updateNote(updatedNote)
        }
    }

    Scaffold(

        topBar = {
            TopAppBar(title = {
                TextFieldComponent(textValue = noteTitle,
                    onValueChanged = { newTitle ->
                        noteTitle = newTitle
                        noteViewModel.updateNote(currentNote.copy(title = newTitle.annotatedString))
                                     },
                    onSelectionChange = { newSelection ->
                        noteTitle = noteTitle.copy(selection = newSelection)
                    },
                    textStyleValue = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                    modifierValue = Modifier.fillMaxWidth()
                )
                              },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        TextComponent(
                            textValue = "back",
                            colorValue = Color.Black,
                            fontSizeValue = 16.sp,
                            )
                        }
                },
                actions = {
                    IconButton(
                        onClick = {
                            noteViewModel.deleteNote(currentNote)
                            navController.popBackStack()
                        }
                    ){
                        TextComponent(
                            textValue = "delete",
                            colorValue = Color.Black,
                            fontSizeValue = 16.sp,
                            )
                    }
                }
            )
        },

        bottomBar = {
            var currentFontSizeState by remember { mutableStateOf(20.sp) }
            RichTextBottomBar(
                onTextStyleToggle = {
                    val newText = toggleStyle(
                        currentString = noteContent.annotatedString,
                        currentSelection = noteContent.selection,
                        newStyle = it
                    )
                    noteContent = noteContent.copy(annotatedString = newText)
                    noteViewModel.updateNote(currentNote.copy(content = newText))
                },

                onColorChange = {
                    val newText = changeColor(currentString = noteContent.annotatedString,
                        currentSelection = noteContent.selection,
                        newColor = it)
                    noteContent = noteContent.copy(annotatedString = newText)
                    noteViewModel.updateNote(currentNote.copy(content = newText))
                },

                onFontSizeChange = {
                    val newText = changeFontSize(noteContent.annotatedString, currentSelection = noteContent.selection, it)

                    currentFontSizeState = it
                    noteContent = noteContent.copy(annotatedString = newText)
                    noteViewModel.updateNote(currentNote.copy(content = newText))
                },
                fontSizeValue = currentFontSizeState,
                modifierValue = Modifier.padding(bottom = 40.dp)
            )
        }
    )
    { innerPadding ->
        TextFieldComponent(
            textValue = noteContent,
            onValueChanged = { newContent ->
                val oldText = noteContent.annotatedString
                val newText = buildAnnotatedString {
                    append(newContent.text)
                    oldText.spanStyles.forEach { span ->
                        addStyle(span.item, span.start, span.end)
                    }
                }
                noteContent = newContent.copy(annotatedString = newText)
                noteViewModel.updateNote(currentNote.copy(content = newContent.annotatedString))
                             },
            onSelectionChange = {
                newSelection ->
                noteContent = noteContent.copy(selection = newSelection)
            },
            modifierValue = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ViewNoteScreenPreview(){
    ViewNoteScreen(rememberNavController(), viewModel())
}