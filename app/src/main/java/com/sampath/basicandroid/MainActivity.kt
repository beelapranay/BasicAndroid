package com.sampath.basicandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.button_toast.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.button_toast)

        // Short and Long Toast
        val button = toast_button
        button.setOnClickListener{
            Toast.makeText(
                this@MainActivity,
                "Short Toast",
                Toast.LENGTH_SHORT
            ).show()
            Toast.makeText(
                this@MainActivity,
                "This is a Long Toast",
                Toast.LENGTH_LONG
            ).show()
        }

    }
}