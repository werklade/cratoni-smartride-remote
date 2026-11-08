package de.werklade.cratoniremote.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.lang.reflect.Modifier
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WerkladeColorsTest {
    @Test
    fun darkColorsMatchDesignTokens() = assertTheme("dark", WerkladeColors.Dark)

    @Test
    fun lightColorsMatchDesignTokens() = assertTheme("light", WerkladeColors.Light)

    private fun assertTheme(theme: String, colors: Any) {
        val json = requireNotNull(javaClass.getResourceAsStream("/design/werklade.tokens.json")) {
            "$theme: missing /design/werklade.tokens.json"
        }.bufferedReader().use { it.readText() }
        val tokenPattern = Regex("\"(dark|light)\\.ui\\.([a-z-]+)\"\\s*:\\s*\"#([0-9a-fA-F]{6})\"")
        val tokens = tokenPattern.findAll(json)
            .filter { it.groupValues[1] == theme }
            .associate { it.groupValues[2] to (0xFF000000L or it.groupValues[3].toLong(16)).toInt() }
        assertTrue("$theme.ui: no six-digit UI tokens found", tokens.isNotEmpty())

        val properties = reflectedColors(theme, colors)
        for ((token, expected) in tokens) {
            val property = token.split('-').mapIndexed { index, part ->
                if (index == 0) part else part.replaceFirstChar { it.uppercaseChar() }
            }.joinToString("")
            assertTrue("$theme.ui.$token: missing Kotlin property $property", property in properties)
            assertEquals("$theme.ui.$token: ARGB value differs", expected, properties.getValue(property))
        }
        for (property in properties.keys) {
            val token = property.replace(Regex("[A-Z]")) { "-${it.value.lowercase()}" }
            assertTrue("$theme.ui.$token: Kotlin property $property has no six-digit UI token", token in tokens)
        }
    }

    private fun reflectedColors(theme: String, colors: Any): Map<String, Int> {
        val type = colors.javaClass
        val properties = mutableMapOf<String, Int>()
        for (getter in type.declaredMethods.filter {
            it.name.startsWith("get") && it.parameterCount == 0 && !it.isSynthetic
        }) {
            val property = getter.name.substringBefore('-').removePrefix("get")
                .replaceFirstChar { it.lowercaseChar() }
            val token = property.replace(Regex("[A-Z]")) { "-${it.value.lowercase()}" }
            assertEquals("$theme.ui.$token: expected a packed Color getter", Long::class.javaPrimitiveType, getter.returnType)
            getter.isAccessible = true
            val packed = getter.invoke(if (Modifier.isStatic(getter.modifiers)) null else colors) as Long
            properties[property] = Color(packed.toULong()).toArgb()
        }
        // Kotlin may expose object vals as static backing fields instead of getters.
        for (field in type.declaredFields.filter { it.name != "INSTANCE" && !it.name.startsWith("$") && !it.isSynthetic }) {
            if (field.name in properties) continue
            val token = field.name.replace(Regex("[A-Z]")) { "-${it.value.lowercase()}" }
            assertEquals("$theme.ui.$token: expected a packed Color field", Long::class.javaPrimitiveType, field.type)
            field.isAccessible = true
            val packed = field.getLong(if (Modifier.isStatic(field.modifiers)) null else colors)
            properties[field.name] = Color(packed.toULong()).toArgb()
        }
        assertTrue("$theme.ui: no Kotlin color properties found", properties.isNotEmpty())
        return properties
    }
}
