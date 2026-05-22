package com.ssk.pagekeeper.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Raw brand tokens — sourced directly from the design spec.
val TextPrimary = Color(0xFF2C2926)
val TextSecondary = Color(0xFF706C61)
val BgMain = Color(0xFFFDFCF8)
val BgCard = Color(0xFFE4CDA8)
val BgActive = Color(0xFFF1EBDF)
val BrandPrimary = Color(0xFFBC7851)
val IconColor = Color(0xFF706C61)
val DividerColor = Color(0xFFE1DDD0)
val StateFinished = Color(0xFF14AF62)
val StateAlert = Color(0xFFDC362E)

// Dark-scheme derivations — light is canonical from the spec; dark is derived using
// standard M3 inversion (foreground/background swap, primaries lightened for contrast).
private val DarkBrandPrimary = Color(0xFFDDA681)
private val DarkPrimaryContainer = Color(0xFF7A4D33)
private val DarkSecondary = Color(0xFFB8B3A6)
private val DarkSecondaryContainer = Color(0xFF4A463E)
private val DarkBackground = Color(0xFF1A1815)
private val DarkOnBackground = Color(0xFFF0EBE2)
private val DarkSurfaceVariant = Color(0xFF2A2622)
private val DarkOnSurfaceVariant = Color(0xFFC5BEAE)
private val DarkOutline = Color(0xFF8A8478)
private val DarkOutlineVariant = Color(0xFF3A3631)
private val DarkStateAlert = Color(0xFFFF7066)

val LightColors = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,
    primaryContainer = BgCard,
    onPrimaryContainer = TextPrimary,
    secondary = TextSecondary,
    onSecondary = Color.White,
    secondaryContainer = BgActive,
    onSecondaryContainer = TextPrimary,
    tertiary = BrandPrimary,
    onTertiary = Color.White,
    background = BgMain,
    onBackground = TextPrimary,
    surface = BgMain,
    onSurface = TextPrimary,
    surfaceVariant = BgActive,
    onSurfaceVariant = TextSecondary,
    outline = IconColor,
    outlineVariant = DividerColor,
    error = StateAlert,
    onError = Color.White,
)

val DarkColors = darkColorScheme(
    primary = DarkBrandPrimary,
    onPrimary = TextPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnBackground,
    secondary = DarkSecondary,
    onSecondary = TextPrimary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnBackground,
    tertiary = DarkBrandPrimary,
    onTertiary = TextPrimary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkBackground,
    onSurface = DarkOnBackground,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
    error = DarkStateAlert,
    onError = TextPrimary,
)
