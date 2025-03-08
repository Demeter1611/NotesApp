package com.example.notesapp.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit

fun toggleStyle(
    currentString: AnnotatedString,
    currentSelection: TextRange,
    newStyle: SpanStyle) : AnnotatedString {

    //Get the existing styles that apply to the selected text
    val existingStyles = currentString.spanStyles
        .filter{
            it.start in currentSelection.min..currentSelection.max
                    || it.end in currentSelection.min..currentSelection.max
        }

    //Checks if newStyle is already applied to currentString
    var exists = false
    existingStyles.forEach{
        if(it.item == newStyle){
            exists = true
        }
    }

    //Builds a new AnnotatedString with the newStyle applied, while keeping the old styles
    val newText = buildAnnotatedString {
        append(currentString.text)
        if(!exists) {
            addStyle(
                style = newStyle,
                start = currentSelection.min,
                end = currentSelection.max
            )
        }
        existingStyles.forEach{
            if(!exists || it.item != newStyle) {
                addStyle(it.item, it.start, it.end)
            }
        }
    }

    return newText
}

fun changeFontSize(currentString: AnnotatedString,
                   currentSelection: TextRange,
                   newFontSize: TextUnit) : AnnotatedString {
    return buildAnnotatedString{
        append(currentString.text)
        currentString.spanStyles.forEach{ span ->
            addStyle(
                span.item,
                span.start,
                span.end
            )
        }
        addStyle(SpanStyle(fontSize = newFontSize),
            start = currentSelection.start,
            end = currentSelection.end)
    }
}

fun changeColor(currentString: AnnotatedString,
                currentSelection: TextRange,
                newColor: Color) : AnnotatedString {
    return buildAnnotatedString{
        append(currentString.text)
        currentString.spanStyles.forEach{ span ->
            addStyle(
                span.item,
                span.start,
                span.end
            )
        }
        addStyle(SpanStyle(color = newColor),
            start = currentSelection.start,
            end = currentSelection.end)
    }
}
