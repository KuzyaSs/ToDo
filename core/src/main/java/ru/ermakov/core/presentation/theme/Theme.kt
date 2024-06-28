package ru.ermakov.core.presentation.theme

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun ToDoTypographyPreview() {
    ToDoTheme {
        val modifier = Modifier.padding(all = ToDoTheme.size.medium)
        Surface(color = ToDoTheme.colors.backSecondary) {
            Column {
                Text(
                    text = "Large title — 32/38",
                    style = ToDoTheme.typography.largeTitle,
                    modifier = modifier
                )
                Text(
                    text = "Title — 20/32",
                    style = ToDoTheme.typography.title,
                    modifier = modifier
                )
                Text(
                    text = "BUTTON — 14/24",
                    style = ToDoTheme.typography.button,
                    modifier = modifier
                )
                Text(
                    text = "Body — 16/20",
                    style = ToDoTheme.typography.body,
                    modifier = modifier
                )
                Text(
                    text = "Subhead — 14/20",
                    style = ToDoTheme.typography.subhead,
                    modifier = modifier
                )
            }
        }
    }
}

@Preview(device = "spec:width=3840dp,height=150dp,dpi=440")
@Preview(
    device = "spec:width=3840dp,height=150dp,dpi=440",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ToDoColorBoxesPreview() {
    ToDoTheme {
        val colorNameToColor = mapOf(
            "Support / Separator #33000000" to ToDoTheme.colors.supportSeparator,
            "Support / Overlay #0F000000" to ToDoTheme.colors.supportOverlay,
            "Label / Primary #000000" to ToDoTheme.colors.labelPrimary,
            "Label / Secondary #99000000" to ToDoTheme.colors.labelSecondary,
            "Label / Tertiary #4D000000" to ToDoTheme.colors.labelTertiary,
            "Label / Disable #26000000" to ToDoTheme.colors.labelDisable,
            "Color / Red #FF3B30" to ToDoTheme.colors.red,
            "Color / Green #34C759" to ToDoTheme.colors.green,
            "Color / Blue #007AFF" to ToDoTheme.colors.blue,
            "Color / Gray #8E8E93" to ToDoTheme.colors.gray,
            "Color /Gray Light #D1D1D6" to ToDoTheme.colors.grayLight,
            "Color / White #FFFFFF" to ToDoTheme.colors.white,
            "Back / Primary #F7F6F2" to ToDoTheme.colors.backPrimary,
            "Back / Secondary #FFFFFF" to ToDoTheme.colors.backSecondary,
            "Back / Elevated #FFFFFF" to ToDoTheme.colors.backElevated,
        )
        Row {
            colorNameToColor.forEach { (colorName, color) ->
                ToDoColorBox(colorName = colorName, color = color)
            }
        }
    }
}

@Composable
fun ToDoColorBox(colorName: String, color: Color, modifier: Modifier = Modifier) {
    Column {
        Text(text = colorName, color = ToDoTheme.colors.white)
        Box(
            modifier = modifier
                .size(240.dp, 100.dp)
                .padding(end = ToDoTheme.size.medium)
                .background(color = color)
        )
    }
}