// app/src/main/java/net/bible/android/view/util/theme/ThemeCssGenerator.kt

package net.bible.android.view.util.theme

import android.graphics.Color

object ThemeCssGenerator {

    fun generateCss(theme: ReadingTheme): String {
        val bg    = colorToHex(theme.background)
        val fg    = colorToHex(theme.foreground)
        val vnum  = colorToHex(theme.verseNumber)
        val hl    = colorToHex(theme.highlight)
        val link  = colorToHex(theme.link)
        val title = colorToHex(theme.sectionTitle)

        return """
            :root {
                --bg:     $bg;
                --fg:     $fg;
                --vnum:   $vnum;
                --hl:     $hl;
                --link:   $link;
                --title:  $title;
            }

            body {
                background-color: var(--bg) !important;
                color:            var(--fg) !important;
                font-family:      ${theme.fontFamily}, Georgia, serif;
                line-height:      1.75;
                padding:          16px;
                transition:       background-color 0.25s, color 0.25s;
            }

            /* Jaenumerointi */
            .verseNumber, sup.verse {
                color:       var(--vnum) !important;
                font-size:   0.7em;
                font-weight: normal;
                margin-right: 3px;
            }

            /* Korostukset */
            .highlighted, mark {
                background-color: var(--hl) !important;
                color:            var(--bg) !important;
                border-radius:    2px;
                padding:          0 2px;
            }

            /* Linkit (ristiin viittaukset) */
            a, .crossRef {
                color:           var(--link) !important;
                text-decoration: none;
                border-bottom:   1px dotted var(--link);
            }

            /* Väliotsikot */
            h1, h2, h3, .sectionTitle {
                color:       var(--title) !important;
                font-weight: 600;
                margin-top:  1.4em;
            }

            /* Valittu jae */
            .currentVerse {
                border-left:  3px solid var(--hl);
                padding-left: 8px;
                margin-left:  -11px;
            }

            /* Footnotes */
            .footnote {
                color:     var(--vnum) !important;
                font-size: 0.85em;
            }
        """.trimIndent()
    }

    private fun colorToHex(colorInt: Int): String {
        return String.format("#%06X", 0xFFFFFF and colorInt)
    }
}