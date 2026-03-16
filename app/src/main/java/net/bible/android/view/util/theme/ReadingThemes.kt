package net.bible.android.view.util.theme

data class ReadingTheme(
    val id: String,
    val displayName: String,
    val background: Int,
    val foreground: Int,
    val verseNumber: Int,
    val highlight: Int,
    val link: Int,
    val sectionTitle: Int,
    val fontFamily: String = "serif"
)

object ReadingThemes {

    val MONOKAI = ReadingTheme(
        id           = "monokai",
        displayName  = "Monokai",
        background   = 0xFF272822.toInt(),
        foreground   = 0xFFF8F8F2.toInt(),
        verseNumber  = 0xFF75715E.toInt(),
        highlight    = 0xFFA6E22E.toInt(),
        link         = 0xFF66D9E8.toInt(),
        sectionTitle = 0xFFE6DB74.toInt()
    )

    val SOLARIZED_LIGHT = ReadingTheme(
        id           = "solarized_light",
        displayName  = "Solarized Light",
        background   = 0xFFFDF6E3.toInt(),
        foreground   = 0xFF657B83.toInt(),
        verseNumber  = 0xFF93A1A1.toInt(),
        highlight    = 0xFF268BD2.toInt(),
        link         = 0xFF2AA198.toInt(),
        sectionTitle = 0xFFCB4B16.toInt()
    )

    val SOLARIZED_DARK = ReadingTheme(
        id           = "solarized_dark",
        displayName  = "Solarized Dark",
        background   = 0xFF002B36.toInt(),
        foreground   = 0xFF839496.toInt(),
        verseNumber  = 0xFF586E75.toInt(),
        highlight    = 0xFF268BD2.toInt(),
        link         = 0xFF2AA198.toInt(),
        sectionTitle = 0xFFCB4B16.toInt()
    )

    val DRACULA = ReadingTheme(
        id           = "dracula",
        displayName  = "Dracula",
        background   = 0xFF282A36.toInt(),
        foreground   = 0xFFF8F8F2.toInt(),
        verseNumber  = 0xFF6272A4.toInt(),
        highlight    = 0xFFBD93F9.toInt(),
        link         = 0xFF8BE9FD.toInt(),
        sectionTitle = 0xFFFF79C6.toInt()
    )

    val GITHUB_LIGHT = ReadingTheme(
        id           = "github_light",
        displayName  = "GitHub Light",
        background   = 0xFFFFFFFF.toInt(),
        foreground   = 0xFF24292E.toInt(),
        verseNumber  = 0xFF6A737D.toInt(),
        highlight    = 0xFF0366D6.toInt(),
        link         = 0xFF0366D6.toInt(),
        sectionTitle = 0xFF005CC5.toInt()
    )

    val NORD = ReadingTheme(
        id           = "nord",
        displayName  = "Nord",
        background   = 0xFF2E3440.toInt(),
        foreground   = 0xFFD8DEE9.toInt(),
        verseNumber  = 0xFF4C566A.toInt(),
        highlight    = 0xFF88C0D0.toInt(),
        link         = 0xFF81A1C1.toInt(),
        sectionTitle = 0xFFA3BE8C.toInt()
    )

    val ALL = listOf(MONOKAI, SOLARIZED_LIGHT, SOLARIZED_DARK, DRACULA, GITHUB_LIGHT, NORD)

    fun findById(id: String): ReadingTheme =
        ALL.firstOrNull { it.id == id } ?: GITHUB_LIGHT
}