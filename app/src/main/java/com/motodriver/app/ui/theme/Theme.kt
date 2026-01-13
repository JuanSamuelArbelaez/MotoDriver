package com.motodriver.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Green500,
    onPrimary = White,
    primaryContainer = Green50,
    onPrimaryContainer = Green700,
    secondary = Blue500,
    onSecondary = White,
    secondaryContainer = Blue50,
    onSecondaryContainer = Blue700,
    error = Red500,
    onError = White,
    errorContainer = Red50,
    onErrorContainer = Red500,
    background = Background,
    onBackground = Grey900,
    surface = White,
    onSurface = Grey900,
    surfaceVariant = Grey50,
    onSurfaceVariant = Grey600,
    outline = Grey100
)

@Composable
fun MotoDriverTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
