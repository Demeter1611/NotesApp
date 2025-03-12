package com.example.notesapp.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.view.utils.ColorConverter
import com.example.notesapp.view.utils.TextStyleConverter

@Composable
fun TextComponent(textValue: String,
                  colorValue: Color,
                  fontSizeValue: TextUnit,
                  fontWeightValue: FontWeight = FontWeight.Normal,
                  textAlignValue: TextAlign = TextAlign.Start,
                  modifierValue: Modifier = Modifier,
){
    Text(text = textValue,
        color = colorValue,
        fontSize = fontSizeValue,
        fontWeight = fontWeightValue,
        textAlign = textAlignValue,
        modifier = modifierValue)
}

@Preview(showBackground = true)
@Composable
fun TextComponentPreview(){
    TextComponent(
        textValue = "This is a text",
        colorValue = Color.Black,
        fontSizeValue = 20.sp,
        fontWeightValue = FontWeight.Normal,
        modifierValue = Modifier
    )
}

@Composable
fun TextFieldComponent(textValue: TextFieldValue,
                       modifierValue: Modifier = Modifier,
                       onValueChanged: (TextFieldValue) -> Unit,
                       onSelectionChange: (TextRange) -> Unit = {},
                       textStyleValue: TextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
){
    BasicTextField(
        value = textValue,
        onValueChange = { newText -> onValueChanged(newText)
                        onSelectionChange(newText.selection)},
        modifier = modifierValue,
        textStyle = textStyleValue,
        keyboardActions = KeyboardActions(
        )
    )
}

@Composable
fun RichTextBottomBar(
    onFontSizeChange: (TextUnit) -> Unit = {},
    onColorChange: (Color) -> Unit = {},
    onTextStyleToggle: (SpanStyle) -> Unit = {},
    fontSizeValue: TextUnit = 16.sp,
    modifierValue: Modifier = Modifier,
) {
    var isFontExpanded by remember { mutableStateOf(false) }
    var isColorExpanded by remember { mutableStateOf(false) }
    var isTextStyleExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifierValue),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            val equalSizeModifier = Modifier.weight(1f)

            ExpandableMenu(
                isExpanded = isTextStyleExpanded,
                onExpandToggle = { isTextStyleExpanded = !isTextStyleExpanded },
                contentList = listOf("Bold", "Italic", "Underline"),
                buttonText = "Text style",
                onItemClick = { style -> onTextStyleToggle(TextStyleConverter.convertStringToTextStyle(style)) },
                modifierValue = equalSizeModifier
            )

            ExpandableMenu(
                isExpanded = isColorExpanded,
                onExpandToggle = { isColorExpanded = !isColorExpanded},
                contentList = listOf("Black", "Red", "Blue", "Green", "Yellow", "Magenta", "Cyan"),
                buttonText = "Color",
                onItemClick = { color -> onColorChange(ColorConverter.convertStringToColor(color))},
                modifierValue = equalSizeModifier,
            )

            ExpandableMenu(
                isExpanded = isFontExpanded,
                onExpandToggle = { isFontExpanded = !isFontExpanded },
                contentList = listOf("12", "16", "20", "24", "28", "32", "36", "40", "44", "48", "62", "66", "70"),
                buttonText = fontSizeValue.value.toInt().toString(),
                onItemClick = { size -> onFontSizeChange(size.toInt().sp) },
                modifierValue = equalSizeModifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RichTextBottomBarPreview(){
    RichTextBottomBar()
}

@Composable
fun ExpandableButton(
    onClick: () -> Unit,
    textValue: String,
    modifierValue: Modifier = Modifier,
){
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier
            .then(modifierValue)
    ){
        TextComponent(
            textValue = textValue,
            colorValue = Color.Black,
            fontSizeValue = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableButtonPreview() {
    ExpandableButton(
        onClick = {},
        textValue = "Button"
    )
}

@Composable
fun <T>ExpandableContent(
    content: List<T>,
    modifier: Modifier = Modifier,
    onClick: (T) -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(80.dp)
            .padding(8.dp)
    ) {
        content.forEach { item ->
            Text(text = item.toString(),
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { onClick(item) }
            )
        }
    }
}

@Composable
fun <T> ExpandableMenu(
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    contentList: List<T>,
    buttonText: String,
    onItemClick: (T) -> Unit,
    modifierValue: Modifier = Modifier
){
    Box(
        modifier = modifierValue,
        contentAlignment = Alignment.BottomCenter
    ){
        ExpandableButton(
            onClick = { onExpandToggle() },
            textValue = buttonText,
            modifierValue = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(animationSpec = tween(300)),
            exit = shrinkVertically(animationSpec = tween(300))
        ){
            ExpandableContent(
                content = contentList,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth(),
                onClick = { item ->
                    onItemClick(item)
                    onExpandToggle()
                }
            )
        }
    }
}