package com.example.notesapp.view.screens
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.view.RichTextBottomBar
import com.example.notesapp.view.TextComponent
import com.example.notesapp.view.TextFieldComponent
import com.example.notesapp.view.utils.changeColor
import com.example.notesapp.view.utils.changeFontSize
import com.example.notesapp.view.utils.toAnnotatedString
import com.example.notesapp.view.utils.toEntity
import com.example.notesapp.view.utils.toggleStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNoteScreen(navController : NavHostController, noteViewModel: NoteViewModel){
    val currentNote = noteViewModel.currentNote.value
    var isDeleted by remember { mutableStateOf(false) }
    if(currentNote == null){
        navController.popBackStack()
        return
    }
    val scope = rememberCoroutineScope()

    var noteTitle by remember { mutableStateOf(TextFieldValue(annotatedString = currentNote.title.toAnnotatedString(),
        selection = TextRange(currentNote.title.text.length)))}

    var noteContent by remember { mutableStateOf(TextFieldValue(annotatedString = currentNote.content.toAnnotatedString(),
    selection = TextRange(currentNote.content.text.length)))}

    DisposableEffect(Unit) {
        onDispose {
            if (!isDeleted) {
                val updatedNote = currentNote.copy(
                    title = noteTitle.annotatedString.toEntity(),
                    content = noteContent.annotatedString.toEntity()
                )
                noteViewModel.updateNote(updatedNote)
            }
        }
    }

    Scaffold(

        topBar = {
            TopAppBar(title = {
                TextFieldComponent(textValue = noteTitle,
                    onValueChanged = { newTitle ->
                        noteTitle = newTitle
                        noteViewModel.updateNote(currentNote.copy(title = newTitle.annotatedString.toEntity()))
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
                            scope.launch {
                                isDeleted = true
                                noteViewModel.deleteNote(currentNote)
                                navController.popBackStack()
                            }
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
                    noteViewModel.updateNote(currentNote.copy(content = newText.toEntity()))
                },

                onColorChange = {
                    val newText = changeColor(currentString = noteContent.annotatedString,
                        currentSelection = noteContent.selection,
                        newColor = it)
                    noteContent = noteContent.copy(annotatedString = newText)
                    noteViewModel.updateNote(currentNote.copy(content = newText.toEntity()))
                },

                onFontSizeChange = {
                    val newText = changeFontSize(noteContent.annotatedString, currentSelection = noteContent.selection, it)

                    currentFontSizeState = it
                    noteContent = noteContent.copy(annotatedString = newText)
                    noteViewModel.updateNote(currentNote.copy(content = newText.toEntity()))
                },
                fontSizeValue = currentFontSizeState,
                modifierValue = Modifier//.padding(bottom = 40.dp)
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
                noteViewModel.updateNote(currentNote.copy(content = newContent.annotatedString.toEntity()))
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