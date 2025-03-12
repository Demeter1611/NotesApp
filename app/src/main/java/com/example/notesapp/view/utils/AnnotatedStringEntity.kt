package com.example.notesapp.view.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable

@Serializable
data class AnnotatedStringEntity(
    val text: String,
    val spans: List<SpanStyleEntity>
)

@Serializable
data class SpanStyleEntity(
    val start: Int,
    val end: Int,
    val textStyleValue: String,
    val colorValue: String,
    val fontSizeValue: Int,
)

fun AnnotatedString.toEntity(): AnnotatedStringEntity {
    return AnnotatedStringEntity(
        text = this.text,
        spans = this.spanStyles.map {
            span ->
            SpanStyleEntity(
                start = span.start,
                end = span.end,
                textStyleValue = TextStyleConverter.convertTextStyleToString(span.item),
                colorValue = ColorConverter.convertColorToString(span.item.color),
                fontSizeValue = span.item.fontSize.value.toInt()
            )
        }
    )
}

fun AnnotatedStringEntity.toAnnotatedString(): AnnotatedString {
//    return AnnotatedString(
//        text = this.text,
//        spanStyles = this.spans.map{ span ->
//            AnnotatedString.Range(
//                item = TextStyleConverter.convertStringToTextStyle(span.textStyleValue),
//                start = span.start,
//                end = span.end
//            )
//        }
//    )
    var result = AnnotatedString(this.text)
    this.spans.forEach { span ->
        result = toggleStyle(
            result,
            currentSelection = TextRange(start = span.start, end = span.end),
            newStyle = TextStyleConverter.convertStringToTextStyle(span.textStyleValue),
        )
        if(span.colorValue != "") {
            result = changeColor(
                result,
                currentSelection = TextRange(start = span.start, end = span.end),
                newColor = ColorConverter.convertStringToColor(span.colorValue),
            )
        }
        if(span.fontSizeValue > 10) {
            result = changeFontSize(
                result,
                currentSelection = TextRange(start = span.start, end = span.end),
                newFontSize = span.fontSizeValue.sp,
            )
        }
    }
    return result
}
