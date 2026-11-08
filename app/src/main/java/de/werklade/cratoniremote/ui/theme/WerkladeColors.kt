package de.werklade.cratoniremote.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Werklade colour tokens, `themes.dark.ui` and `themes.light.ui`.
 *
 * Source of truth is the Werklade design repo (`design/tokens/werklade.json`); the values below are checked
 * against that file by name (property name = camelCase of the token key, e.g. `bg-panel-alt` -> `bgPanelAlt`).
 * Tokens with an alpha channel (`backdrop`, `shadow`, `warning-bg`, `error-bg`, `indent-guide`) are not
 * included. Do not edit values here without changing the tokens first.
 */
object WerkladeColors {

    object Dark {
        val bgContent = Color(0xFF141B22)
        val bgApp = Color(0xFF1F2933)
        val bgPanel = Color(0xFF26313D)
        val bgPanelAlt = Color(0xFF2C3946)
        val bgToolbar = Color(0xFF18212A)
        val bgMenubar = Color(0xFF141B22)
        val bgInput = Color(0xFF1A232C)
        val bgHover = Color(0xFF33414F)
        val bgActive = Color(0xFF3D4D5D)
        val bgTab = Color(0xFF222C36)
        val bgTabActive = Color(0xFF33414F)
        val fgPrimary = Color(0xFFE4E7EB)
        val fgSecondary = Color(0xFFB8C0C9)
        val fgMuted = Color(0xFF7B8794)
        val fgInverse = Color(0xFF1F2933)
        val accent = Color(0xFFF08A24)
        val accentHover = Color(0xFFF5A04A)
        val accentHoverFill = Color(0xFFF5A04A)
        val border = Color(0xFF2F3B48)
        val borderStrong = Color(0xFF3E4C59)
        val focusRing = Color(0xFFF08A24)
        val danger = Color(0xFFE5534B)
        val warning = Color(0xFFF0B429)
        val success = Color(0xFF7BC96F)
        val onAccent = Color(0xFF1F2933)
        val monitorOk = Color(0xFF7BC96F)
        val monitorWarn = Color(0xFFF0B429)
        val monitorCritical = Color(0xFFE5534B)
        val selection = Color(0xFF3D4D5D)
        val diffAdded = Color(0xFF254E37)
        val diffDeleted = Color(0xFF602C36)
        val diffAddedText = Color(0xFF35724B)
        val diffDeletedText = Color(0xFF8A3D4B)
    }

    object Light {
        val bgContent = Color(0xFFFFFFFF)
        val bgApp = Color(0xFFF4F5F7)
        val bgPanel = Color(0xFFF4F5F7)
        val bgPanelAlt = Color(0xFFE9ECEF)
        val bgToolbar = Color(0xFFE4E7EB)
        val bgMenubar = Color(0xFFE4E7EB)
        val bgInput = Color(0xFFFFFFFF)
        val bgHover = Color(0xFFDDE2E7)
        val bgActive = Color(0xFFCBD2D9)
        val bgTab = Color(0xFFE4E7EB)
        val bgTabActive = Color(0xFFFFFFFF)
        val fgPrimary = Color(0xFF1F2933)
        val fgSecondary = Color(0xFF3E4C59)
        val fgMuted = Color(0xFF616E7C)
        val fgInverse = Color(0xFFFFFFFF)
        val accent = Color(0xFFD9731A)
        val accentHover = Color(0xFFB35D10)
        val accentHoverFill = Color(0xFFE07D22)
        val border = Color(0xFFD5DBE1)
        val borderStrong = Color(0xFFCBD2D9)
        val focusRing = Color(0xFFD9731A)
        val danger = Color(0xFFC62828)
        val warning = Color(0xFFB7791F)
        val success = Color(0xFF2F8A3A)
        val onAccent = Color(0xFF1F2933)
        val monitorOk = Color(0xFF2F8A3A)
        val monitorWarn = Color(0xFFB7791F)
        val monitorCritical = Color(0xFFC62828)
        val selection = Color(0xFFFDE8D2)
        val diffAdded = Color(0xFFD9F4DF)
        val diffDeleted = Color(0xFFFFE0E0)
        val diffAddedText = Color(0xFFA4E4B3)
        val diffDeletedText = Color(0xFFFFB9B9)
    }
}
