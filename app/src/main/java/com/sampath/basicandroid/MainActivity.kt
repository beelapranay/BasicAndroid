package com.sampath.basicandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.button_toast.*
import kotlinx.android.synthetic.main.tts_layout.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.button_toast)
        setContentView(R.layout.tts_layout)

        // Short and Long Toast
//        val button = toast_button
//        button.setOnClickListener{
//            Toast.makeText(
//                this@MainActivity,
//                "Short Toast",
//                Toast.LENGTH_SHORT
//            ).show()
//            Toast.makeText(
//                this@MainActivity,
//                "This is a Long Toast",
//                Toast.LENGTH_LONG
//            ).show()
//        }

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
    }
}