package com.hfad.designsystem.components.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import org.jetbrains.annotations.VisibleForTesting

val DarkColorScheme = darkColorScheme(
    primary = FzPrimaryDark,
    secondary = FzSecondaryDark,
    tertiary = FzTertiaryDark,
    onPrimary = FzOnPrimaryDark,
    onSecondary = FzOnSecondaryDark,
    onTertiary = FzOnTertiaryDark,
    error = FzErrorDark,
    primaryContainer = FzPrimaryDarkContainer,
    secondaryContainer = FzSecondaryDarkContainer,
    tertiaryContainer = FzTertiaryDarkContainer,
    errorContainer = FzErrorDarkContainer,
    surfaceDim = FzSurfaceDimDark,
    surfaceBright = FzSurfaceBrightDark,
    surface = FzSurfaceDark
)

val LightColorScheme = lightColorScheme(
    primary = FzPrimary,
    secondary = FzSecondary,
    tertiary = FzTertiary,
    onPrimary = FzOnPrimary,
    onSecondary = FzOnSecondary,
    onTertiary = FzOnTertiary,
    error = FzErrorLight,
    primaryContainer = FzPrimaryContainer,
    secondaryContainer = FzSecondaryContainer,
    tertiaryContainer = FzTertiaryContainer,
    errorContainer = FzErrorContainer,
    surfaceDim = FzSurfaceDim,
    surfaceBright = FzSurfaceBright,
    surface = FzSurface
)

@Composable
fun FactorizeroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}