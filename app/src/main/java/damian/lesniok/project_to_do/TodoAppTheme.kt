package damian.lesniok.project_to_do

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import damian.lesniok.project_to_do.ui.theme.Purple200
import damian.lesniok.project_to_do.ui.theme.Purple500
import damian.lesniok.project_to_do.ui.theme.Purple700
import damian.lesniok.project_to_do.ui.theme.Shapes
import damian.lesniok.project_to_do.ui.theme.Teal200
import damian.lesniok.project_to_do.ui.theme.Typography

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
