package ru.ermakov.core.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class ToDoTypography(
    val largeTitle: TextStyle,
    val title: TextStyle,
    val button: TextStyle,
    val body: TextStyle,
    val subhead: TextStyle
)

val LocalToDoTypography = staticCompositionLocalOf<ToDoTypography> {
    error("No typography provided")
}