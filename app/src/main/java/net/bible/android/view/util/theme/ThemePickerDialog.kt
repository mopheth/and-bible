package net.bible.android.view.util.theme

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ThemePickerDialog(
    private val context: Context,
    private val themeManager: ThemeManager,
    private val onThemeSelected: (ReadingTheme) -> Unit
) {
    private val sampleHtml = """
        <h3 style="margin:0 0 10px">Jumalan rakkaus</h3>
        <sup class="verseNumber">16</sup>
        Sillä niin on Jumala <mark>maailmaa rakastanut</mark>, että hän antoi
        <a href="#">ainokaisen Poikansa</a>, ettei yksikään, joka häneen uskoo,
        hukkuisi, vaan hänellä olisi iankaikkinen elämä.
        <br><br>
        <sup class="verseNumber">17</sup>
        Sillä ei Jumala lähettänyt Poikaansa maailmaan tuomitsemaan maailmaa,
        vaan sitä varten, että maailma hänen kauttaan pelastuisi.
        <br><br>
        <span class="footnote">— Joh. 3:16–17 (KR 1933/38)</span>
    """.trimIndent()

    fun show() {
        var currentTheme = themeManager.currentTheme

        // ── Rakennetaan dialog-layout koodissa (ei erillistä XML:ää) ──────
        val root = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 8, 0, 0)
        }

        // Otsikko teemalistalle
        val label = TextView(context).apply {
            text = "Valitse teema"
            textSize = 11f
            setTextColor(0xFF888888.toInt())
            setPadding(40, 0, 0, 8)
        }
        root.addView(label)

        // Horisontaalinen teemavalitsin
        val recycler = RecyclerView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 130.dpToPx(context)
            )
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setPadding(16.dpToPx(context), 8.dpToPx(context), 16.dpToPx(context), 8.dpToPx(context))
            clipToPadding = false
        }
        root.addView(recycler)

        // Esikatselu-otsikko
        val previewLabel = TextView(context).apply {
            text = "Esikatselu"
            textSize = 11f
            setTextColor(0xFF888888.toInt())
            setPadding(40, 8, 0, 4)
        }
        root.addView(previewLabel)

        // WebView-esikatselu
        val previewWebView = WebView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 200.dpToPx(context)
            ).apply { setMargins(16.dpToPx(context), 0, 16.dpToPx(context), 8.dpToPx(context)) }
            settings.javaScriptEnabled = false
        }
        root.addView(previewWebView)

        // ── Esikatselu-päivitys ───────────────────────────────────────────
        fun updatePreview(theme: ReadingTheme) {
            val css = ThemeCssGenerator.generateCss(theme)
            val html = """
                <!DOCTYPE html><html><head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width,initial-scale=1">
                <style>
                  $css
                  body { padding: 16px; font-size: 15px; }
                  sup.verseNumber { font-size: 0.65em; }
                </style>
                </head><body>$sampleHtml</body></html>
            """.trimIndent()
            previewWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
            previewWebView.setBackgroundColor(theme.background)
        }

        // ── Adapter ───────────────────────────────────────────────────────
        val adapter = ThemeChipAdapter(
            themes = ReadingThemes.ALL,
            selected = currentTheme
        ) { chosen ->
            currentTheme = chosen
            updatePreview(chosen)
        }
        recycler.adapter = adapter

        // Näytä nykyinen teema heti
        updatePreview(currentTheme)

        // ── Dialog ────────────────────────────────────────────────────────
        AlertDialog.Builder(context)
            .setView(root)
            .setTitle(R.string.reading_theme)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                themeManager.currentTheme = currentTheme
                onThemeSelected(currentTheme)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    // ── Extension helper ─────────────────────────────────────────────────
    private fun Int.dpToPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()
}


// ── ThemeChipAdapter ─────────────────────────────────────────────────────────
private class ThemeChipAdapter(
    private val themes: List<ReadingTheme>,
    private var selected: ReadingTheme,
    private val onSelect: (ReadingTheme) -> Unit
) : RecyclerView.Adapter<ThemeChipAdapter.ViewHolder>() {

    inner class ViewHolder(val root: LinearLayout) : RecyclerView.ViewHolder(root) {
        val swatches: LinearLayout = root.getChildAt(0) as LinearLayout
        val name: TextView         = root.getChildAt(1) as TextView
        val check: TextView        = root.getChildAt(2) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ctx = parent.context
        val dp = { n: Int -> (n * ctx.resources.displayMetrics.density).toInt() }

        // Väripalkki
        val swatches = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dp(5) }
        }

        // Teeman nimi
        val name = TextView(ctx).apply {
            textSize = 10f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dp(3) }
        }

        // Valintamerkki
        val check = TextView(ctx).apply {
            text = "✓ Valittu"
            textSize = 9f
        }

        val card = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.MarginLayoutParams(dp(115), dp(90)).apply {
                marginEnd = dp(8)
            }
            setPadding(dp(10), dp(8), dp(10), dp(8))
            background = GradientDrawable().apply {
                cornerRadius = dp(10).toFloat()
            }
            addView(swatches)
            addView(name)
            addView(check)
        }

        return ViewHolder(card)
    }

    override fun getItemCount() = themes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theme = themes[position]
        val isSelected = theme.id == selected.id
        val dp = { n: Int ->
            (n * holder.root.context.resources.displayMetrics.density).toInt()
        }

        // Kortin taustaväri + reunaviiva
        (holder.root.background as GradientDrawable).apply {
            setColor(theme.background)
            setStroke(if (isSelected) dp(3) else 0, theme.highlight)
        }

        // Väripalkit
        holder.swatches.removeAllViews()
        listOf(theme.background, theme.foreground, theme.highlight,
               theme.sectionTitle, theme.link)
            .forEachIndexed { i, color ->
                holder.swatches.addView(View(holder.root.context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        if (i == 0) dp(18) else dp(12), dp(10)
                    ).apply { marginEnd = dp(3) }
                    background = GradientDrawable().apply {
                        setColor(color)
                        cornerRadius = dp(3).toFloat()
                        setStroke(1, if (isColorLight(theme.background))
                            0x20000000 else 0x20FFFFFF)
                    }
                })
            }

        // Nimi
        holder.name.text = theme.displayName
        holder.name.setTextColor(theme.foreground)

        // Valintamerkki
        holder.check.visibility = if (isSelected) View.VISIBLE else View.GONE
        holder.check.setTextColor(theme.highlight)

        holder.root.setOnClickListener {
            selected = theme
            notifyDataSetChanged()
            onSelect(theme)
        }
    }

    private fun isColorLight(color: Int): Boolean {
        val r = Color.red(color) / 255.0
        val g = Color.green(color) / 255.0
        val b = Color.blue(color) / 255.0
        return (0.2126 * r + 0.7152 * g + 0.0722 * b) > 0.5
    }
}