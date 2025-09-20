package com.example.counter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {

    private var counter: Int = 0
    private var toastNegativeAmount: Toast? = null

    private lateinit var amountText: TextView
    private lateinit var decrementButton: Button
    private lateinit var incrementButton: Button
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val windowInsetsController = androidx.core.view.WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = true
        windowInsetsController.isAppearanceLightNavigationBars = true

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q)
            window.isNavigationBarContrastEnforced = false

        amountText = findViewById<TextView>(R.id.amount)
        decrementButton = findViewById<Button>(R.id.decrementButton)
        incrementButton = findViewById<Button>(R.id.incrementButton)
        resetButton = findViewById<Button>(R.id.resetButton)

        decrementButton.setOnClickListener { decrementCounter() }
        incrementButton.setOnClickListener { incrementCounter() }
        resetButton.setOnClickListener { resetCounter() }
    }

    fun setNewAmountUi(newAmount: Int) {
        amountText.text = newAmount.toString()
    }

    fun decrementCounter() {
        if(counter == 0) {
            showMessageNegativeAmount()
            return
        }

        counter -= 1
        setNewAmountUi(counter)
    }

    fun incrementCounter() {
        counter++
        setNewAmountUi(counter)
    }

    fun resetCounter() {
        counter = 0
        setNewAmountUi(counter)
    }

    fun showMessageNegativeAmount() {
        toastNegativeAmount?.cancel()

        toastNegativeAmount = Toast.makeText(this, "The counter doesn't support negative values", Toast.LENGTH_SHORT)
        toastNegativeAmount?.show()
    }
}