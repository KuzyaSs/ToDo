package ru.ermakov.core.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

data class ToDoSize(
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
)

val LocalToDoSize = staticCompositionLocalOf<ToDoSize> {
    error("No size provided")
}