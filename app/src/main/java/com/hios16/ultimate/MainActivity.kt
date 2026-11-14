package com.hios16.ultimate

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun rounded(color: Int, radius: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(15, 20, 40)
        window.navigationBarColor = Color.rgb(15, 20, 40)

        showHome()
    }

    private fun showHome() {
        val background = Color.rgb(15, 20, 40)
        val white = Color.WHITE

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(background)
        }

        val scroll = ScrollView(this).apply {
            isFillViewport = true
        }

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(18), dp(18), dp(20))
        }

        val brand = TextView(this).apply {
            text = "HiOS 16"
            textSize = 30f
            setTextColor(white)
            setPadding(0, dp(12), 0, dp(4))
        }
        page.addView(brand)

        val subtitle = TextView(this).apply {
            text = "ULTIMATE  •  YOUR HOME"
            textSize = 11f
            setTextColor(Color.LTGRAY)
            letterSpacing = 0.12f
            setPadding(0, 0, 0, dp(24))
        }
        page.addView(subtitle)

        val clockCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(
                    Color.rgb(66, 75, 150),
                    Color.rgb(34, 45, 91),
                    Color.rgb(28, 35, 66)
                )
            ).apply {
                cornerRadius = dp(24).toFloat()
            }
            setPadding(dp(12), dp(20), dp(12), dp(20))
        }

        val clock = TextView(this).apply {
            text = java.text.SimpleDateFormat(
                "hh:mm",
                java.util.Locale.getDefault()
            ).format(java.util.Date())
            textSize = 52f
            setTextColor(white)
            gravity = Gravity.CENTER
        }
        clockCard.addView(clock)

        val date = TextView(this).apply {
            text = java.text.SimpleDateFormat(
                "EEEE, dd MMMM",
                java.util.Locale.getDefault()
            ).format(java.util.Date())
            textSize = 14f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }
        clockCard.addView(date)

        page.addView(
            clockCard,
            LinearLayout.LayoutParams(-1, dp(150)).apply {
                bottomMargin = dp(28)
            }
        )

        val appTitle = TextView(this).apply {
            text = "My Apps"
            textSize = 22f
            setTextColor(white)
            setPadding(0, 0, 0, dp(16))
        }
        page.addView(appTitle)

        val grid = GridLayout(this).apply {
            columnCount = 4
            useDefaultMargins = false
        }

        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val apps = packageManager.queryIntentActivities(intent, 0)
            .sortedBy {
                it.loadLabel(packageManager).toString().lowercase()
            }

        apps.forEach { info ->

            val tile = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                isClickable = true
                isFocusable = true
                setPadding(dp(3), dp(8), dp(3), dp(8))
                background = rounded(Color.TRANSPARENT, 16)
            }

            val icon = ImageView(this).apply {
                setImageDrawable(info.loadIcon(packageManager))
                scaleType = ImageView.ScaleType.FIT_CENTER
            }

            tile.addView(
                icon,
                LinearLayout.LayoutParams(dp(48), dp(48))
            )

            val label = TextView(this).apply {
                text = info.loadLabel(packageManager).toString()
                textSize = 11f
                setTextColor(white)
                gravity = Gravity.CENTER
                maxLines = 1
                ellipsize = android.text.TextUtils.TruncateAt.END
                setPadding(0, dp(7), 0, 0)
            }

            tile.addView(
                label,
                LinearLayout.LayoutParams(-1, dp(28))
            )

            tile.setOnClickListener {
                val launch = packageManager.getLaunchIntentForPackage(
                    info.activityInfo.packageName
                )
                if (launch != null) startActivity(launch)
            }

            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = dp(100)
                columnSpec = GridLayout.spec(
                    GridLayout.UNDEFINED, 1f
                )
                setMargins(dp(2), dp(2), dp(2), dp(8))
            }

            grid.addView(tile, params)
        }

        if (apps.isEmpty()) {
            val empty = TextView(this).apply {
                text = "No apps found"
                setTextColor(white)
                textSize = 15f
            }
            page.addView(empty)
        } else {
            page.addView(grid)
        }

        scroll.addView(page)
        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, -1)
        )

        setContentView(root)
    }
}
