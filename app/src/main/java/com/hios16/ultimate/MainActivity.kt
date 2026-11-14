package com.hios16.ultimate

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding

class MainActivity : AppCompatActivity() {
    private val bg = Color.rgb(14, 20, 38)
    private val card = Color.rgb(31, 42, 67)
    private val white = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        showHome()
    }

    private fun showHome() {
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
            setPadding(dp(18))
        }

        val header = TextView(this).apply {
            text = "HiOS 16"
            textSize = 30f
            setTextColor(white)
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, dp(24), 0, dp(8))
        }
        page.addView(header)

        val subtitle = TextView(this).apply {
            text = "Ultimate Launcher  •  Android 5+"
            textSize = 13f
            setTextColor(Color.LTGRAY)
            setPadding(0, 0, 0, dp(22))
        }
        page.addView(subtitle)

        val clock = TextView(this).apply {
            text = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())
            textSize = 42f
            setTextColor(white)
            gravity = Gravity.CENTER
            setPadding(0, dp(22), 0, dp(22))
            setBackgroundColor(card)
        }
        page.addView(clock, LinearLayout.LayoutParams(-1, dp(110)))

        val appTitle = TextView(this).apply {
            text = "  আপনার অ্যাপ"
            textSize = 18f
            setTextColor(white)
            setPadding(0, dp(22), 0, dp(10))
        }
        page.addView(appTitle)

        val apps = packageManager.queryIntentActivities(
            Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0
        ).sortedBy { it.loadLabel(packageManager).toString().lowercase() }

        apps.forEach { info ->
            val row = TextView(this).apply {
                text = "  ${info.loadLabel(packageManager)}"
                textSize = 16f
                setTextColor(white)
                setPadding(dp(14))
                setBackgroundColor(card)
                isClickable = true
                isFocusable = true
                setOnClickListener {
                    val launch = packageManager.getLaunchIntentForPackage(info.activityInfo.packageName)
                    if (launch != null) startActivity(launch)
                }
            }
            val lp = LinearLayout.LayoutParams(-1, dp(52))
            lp.bottomMargin = dp(7)
            page.addView(row, lp)
        }

        val note = TextView(this).apply {
            text = "প্রথম সংস্করণ: Home Screen ও অ্যাপ চালু করার বেস তৈরি হয়েছে। পরের ধাপে আইকন গ্রিড, ওয়ালপেপার ও কাস্টমাইজেশন যোগ হবে।"
            textSize = 12f
            setTextColor(Color.LTGRAY)
            setPadding(0, dp(18), 0, dp(12))
        }
        page.addView(note)
        setContentView(page)
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
