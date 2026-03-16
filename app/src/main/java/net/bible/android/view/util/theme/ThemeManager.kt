// app/src/main/java/net/bible/android/view/util/theme/ThemeManager.kt

package net.bible.android.view.util.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ThemeManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("reading_theme_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_THEME_ID = "selected_reading_theme"
        private const val DEFAULT_ID   = "github_light"
    }

    var currentTheme: ReadingTheme
        get() = ReadingThemes.findById(prefs.getString(KEY_THEME_ID, DEFAULT_ID)!!)
        set(theme) = prefs.edit { putString(KEY_THEME_ID, theme.id) }

    fun currentCss(): String = ThemeCssGenerator.generateCss(currentTheme)
}