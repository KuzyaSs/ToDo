package ru.ermakov.core.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

object ToDoTheme {
    val colors: ToDoColors
        @Composable
        @ReadOnlyComposable
        get() = LocalToDoColors.current

    val typography: ToDoTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalToDoTypography.current

    val size: ToDoSize
        @Composable
        @ReadOnlyComposable
        get() = LocalToDoSize.current
}

@Composable
fun ToDoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val typography = ToDoTypography(
        largeTitle = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            color = colors.labelPrimary
        ),
        title = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = colors.labelPrimary
        ),
        button = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = colors.labelPrimary
        ),
        body = TextStyle(fontSize = 16.sp, color = colors.labelPrimary),
        subhead = TextStyle(fontSize = 14.sp, color = colors.labelPrimary)
    )

    val size = ToDoSize(
        extraSmall = 4.dp,
        small = 8.dp,
        medium = 16.dp,
        large = 24.dp,
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.backPrimary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalToDoColors provides colors,
        LocalToDoTypography provides typography,
        LocalToDoSize provides size,
        content = content
    )
}