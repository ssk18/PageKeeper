package com.ssk.pagekeeper.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Brand-specific roles the M3 ColorScheme has no slot for. Consumed via MaterialTheme.extraColors.
@Immutable
data class PageKeeperExtraColors(
    val stateFinished: Color,
    val stateAlert: Color,
)

private val LightExtraColors = PageKeeperExtraColors(
    stateFinished = StateFinished,
    stateAlert = StateAlert,
)

private val DarkExtraColors = PageKeeperExtraColors(
    stateFinished = Color(0xFF4FD68C),
    stateAlert = Color(0xFFFF7066),
)

private val LocalExtraColors = staticCompositionLocalOf { LightExtraColors }

@Composable
fun PageKeeperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    val extra = if (darkTheme) DarkExtraColors else LightExtraColors

    CompositionLocalProvider(LocalExtraColors provides extra) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = PageKeeperTypography,
            content = content,
        )
    }
}

val MaterialTheme.extraColors: PageKeeperExtraColors
    @Composable
    @ReadOnlyComposable
    get() = LocalExtraColors.current
