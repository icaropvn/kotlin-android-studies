package com.example.counter

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var count: Int = 0
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvCounter = findViewById<TextView>(R.id.tvCounter)
        val btnDecrement = findViewById<Button>(R.id.btnDecrement)
        val btnIncrement = findViewById<Button>(R.id.btnIncrement)
        val btnTheme = findViewById<Button>(R.id.btnTheme)

        fun updateCounter() {
            tvCounter.text = count.toString()
        }

        fun isDarkMode(): Boolean {
            val mask = resources.configuration.uiMode and UI_MODE_NIGHT_MASK
            return mask == UI_MODE_NIGHT_YES
        }

        btnIncrement.setOnClickListener {
            count++
            updateCounter()
        }

        btnDecrement.setOnClickListener {
            if(count > 0) {
                count--
                updateCounter()
            }
            else {
                toast?.cancel()
                toast = Toast.makeText(this, "O contador não permite números negativos", Toast.LENGTH_SHORT)
                toast?.show()
            }
        }

        btnTheme.setOnClickListener {
            val darkMode = isDarkMode()
            AppCompatDelegate.setDefaultNightMode( if(darkMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES )
        }
    }
}