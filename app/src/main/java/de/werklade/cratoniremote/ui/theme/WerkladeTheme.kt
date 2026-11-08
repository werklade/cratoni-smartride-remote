package de.werklade.cratoniremote.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/** Status colours Material 3 has no role for (it only knows `error`). */
@Immutable
class WerkladeStatusColors(val success: Color, val warning: Color)

private val DarkStatusColors = WerkladeStatusColors(
    success = WerkladeColors.Dark.success,
    warning = WerkladeColors.Dark.warning,
)

private val LightStatusColors = WerkladeStatusColors(
    success = WerkladeColors.Light.success,
    warning = WerkladeColors.Light.warning,
)

private val LocalWerkladeStatusColors = staticCompositionLocalOf<WerkladeStatusColors> { error("WerkladeTheme missing") }

/** Success and warning colour of the active Werklade theme, e.g. `MaterialTheme.statusColors.success`. */
val MaterialTheme.statusColors: WerkladeStatusColors
    @Composable
    @ReadOnlyComposable
    get() = LocalWerkladeStatusColors.current

// Orange is an accent only: primary (buttons, active elements) is the one place it appears as a fill, so the
// containers of primary/secondary/tertiary are slate steps, and errors use danger, never orange.
private val DarkScheme: ColorScheme = WerkladeColors.Dark.let { c ->
    darkColorScheme(
        primary = c.accent,
        onPrimary = c.onAccent,
        primaryContainer = c.bgActive,
        onPrimaryContainer = c.fgPrimary,
        inversePrimary = WerkladeColors.Light.accent,
        secondary = c.fgSecondary,
        onSecondary = c.fgInverse,
        secondaryContainer = c.bgPanelAlt,
        onSecondaryContainer = c.fgPrimary,
        tertiary = c.fgSecondary,
        onTertiary = c.fgInverse,
        tertiaryContainer = c.bgPanelAlt,
        onTertiaryContainer = c.fgPrimary,
        background = c.bgContent,
        onBackground = c.fgPrimary,
        surface = c.bgContent,
        onSurface = c.fgPrimary,
        surfaceVariant = c.bgPanel,
        onSurfaceVariant = c.fgSecondary,
        surfaceTint = c.bgContent, // same as surface: no elevation tint, the design is flat
        inverseSurface = c.fgPrimary,
        inverseOnSurface = c.fgInverse,
        error = c.danger,
        onError = c.bgContent, // fgInverse on danger would be 3.99:1
        errorContainer = c.diffDeleted,
        onErrorContainer = c.fgPrimary,
        outline = c.borderStrong,
        outlineVariant = c.border,
        scrim = Color.Black,
        surfaceBright = c.bgHover,
        surfaceDim = c.bgMenubar,
        surfaceContainerLowest = c.bgMenubar,
        surfaceContainerLow = c.bgToolbar,
        surfaceContainer = c.bgPanel,
        surfaceContainerHigh = c.bgPanelAlt,
        surfaceContainerHighest = c.bgHover,
    )
}

private val LightScheme: ColorScheme = WerkladeColors.Light.let { c ->
    lightColorScheme(
        primary = c.accent,
        // User decision 2026-11-08: white on #d9731a fails WCAG AA, so light deviates from token on-accent.
        onPrimary = c.fgPrimary,
        primaryContainer = c.bgActive,
        onPrimaryContainer = c.fgPrimary,
        inversePrimary = WerkladeColors.Dark.accent,
        secondary = c.fgSecondary,
        onSecondary = c.fgInverse,
        secondaryContainer = c.bgToolbar,
        onSecondaryContainer = c.fgPrimary,
        tertiary = c.fgSecondary,
        onTertiary = c.fgInverse,
        tertiaryContainer = c.bgToolbar,
        onTertiaryContainer = c.fgPrimary,
        background = c.bgContent,
        onBackground = c.fgPrimary,
        surface = c.bgContent,
        onSurface = c.fgPrimary,
        surfaceVariant = c.bgPanelAlt,
        onSurfaceVariant = c.fgSecondary,
        surfaceTint = c.bgContent,
        inverseSurface = c.fgPrimary,
        inverseOnSurface = c.fgInverse,
        error = c.danger,
        onError = c.fgInverse,
        errorContainer = c.diffDeleted,
        onErrorContainer = c.fgPrimary,
        outline = c.borderStrong,
        outlineVariant = c.border,
        scrim = Color.Black,
        surfaceBright = c.bgContent,
        surfaceDim = c.bgToolbar,
        surfaceContainerLowest = c.bgContent,
        surfaceContainerLow = c.bgApp,
        surfaceContainer = c.bgPanelAlt,
        surfaceContainerHigh = c.bgToolbar,
        surfaceContainerHighest = c.bgHover,
    )
}

/** Shared 4 dp corners for Werklade buttons, which do not inherit MaterialTheme shapes. */
val WerkladeButtonShape: Shape = RoundedCornerShape(4.dp)

private val WerkladeShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(4.dp),
    extraLarge = RoundedCornerShape(4.dp),
)

/**
 * Werklade design (dark and light) for Material 3. Follows the system setting by default; no dynamic colour.
 *
 * The window background in `res/values/colors.xml` and `res/values-night/colors.xml` (`werklade_window_background`) is the `background` of the
 * respective scheme, so the first frame before Compose draws has the same colour.
 */
@Composable
fun WerkladeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkScheme else LightScheme
    val selection = if (darkTheme) WerkladeColors.Dark.selection else WerkladeColors.Light.selection
    val selectionColors = TextSelectionColors(handleColor = colorScheme.primary, backgroundColor = selection)
    CompositionLocalProvider(
        LocalWerkladeStatusColors provides if (darkTheme) DarkStatusColors else LightStatusColors,
        LocalTextSelectionColors provides selectionColors,
    ) {
        MaterialTheme(colorScheme = colorScheme, shapes = WerkladeShapes, content = content)
    }
}
