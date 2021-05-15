package com.sampath.basicandroid

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.alert.*
import kotlinx.android.synthetic.main.button_toast.*
import kotlinx.android.synthetic.main.listview.*
import kotlinx.android.synthetic.main.tts_layout.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alert)
        setContentView(R.layout.button_toast)
        setContentView(R.layout.tts_layout)
        setContentView(R.layout.listview)

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

        // Text to Speech Conversion
        var ttsText = tts_text
        var ttsButton = tts_button
        lateinit var tts : TextToSpeech

         tts = TextToSpeech(
            this@MainActivity,
            TextToSpeech.OnInitListener {
                if(it == TextToSpeech.SUCCESS){
                    var result = tts.setLanguage(Locale.US)

                    if(result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language is not present")
                    }
                    else{
                        ttsButton.isEnabled = true
                    }
                }
                else{
                    Log.e("TTS", "TTS Failed")
                }
            })

         ttsButton.setOnClickListener{
             tts.speak(
                 ttsText.text.toString(),
                 TextToSpeech.QUEUE_FLUSH,
                 null,
                 null
             );
         }

        // Basic Listview using ArrayAdapter
        val array = arrayOf("United states", "Japan", "China", "Sri Lanka", "Russia", "Europe",
            "Spain", "Swiss", "Denmark", "Australia", "London", "West Indies", "Iraq", "Kabul",
            "Dubai", "Israel", "Nepal", "Iran")

            val adapter = ArrayAdapter(this@MainActivity,
                R.layout.listview_item, array)

            val listView: ListView = listview
            listView.setAdapter(adapter)

        // Alert
        btnShowAlert.setOnClickListener{

            val dialogBuilder = AlertDialog.Builder(this@MainActivity)
            dialogBuilder.setMessage("Do you want to proceed?")

                .setPositiveButton("Yes") { _, _ ->
                    finish()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

            val alert = dialogBuilder.create()
            alert.setTitle("Alert")
            alert.show()
        }

    }
}