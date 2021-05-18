package com.sampath.basicandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.alert.*
import kotlinx.android.synthetic.main.button_toast.*
import kotlinx.android.synthetic.main.implicit_intent.*
import kotlinx.android.synthetic.main.listview.*
import kotlinx.android.synthetic.main.radio_button.*
import kotlinx.android.synthetic.main.spinner.*
import kotlinx.android.synthetic.main.toggle_button.*
import kotlinx.android.synthetic.main.tts_layout.*
import kotlinx.android.synthetic.main.webview.*
import kotlinx.android.synthetic.main.webview.submitButton
import kotlinx.android.synthetic.main.write_read.*
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.button_toast)
        setContentView(R.layout.tts_layout)
        setContentView(R.layout.listview)
        setContentView(R.layout.alert)
        setContentView(R.layout.webview)
        setContentView(R.layout.toggle_button)
        setContentView(R.layout.spinner)
        setContentView(R.layout.radio_button)
        setContentView(R.layout.write_read)
        setContentView(R.layout.implicit_intent)

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

         tts = TextToSpeech(this@MainActivity) {
             if (it == TextToSpeech.SUCCESS) {
                 var result = tts.setLanguage(Locale.US)

                 if (result == TextToSpeech.LANG_MISSING_DATA ||
                     result == TextToSpeech.LANG_NOT_SUPPORTED
                 ) {
                     Log.e("TTS", "Language is not present")
                 } else {
                     ttsButton.isEnabled = true
                 }
             } else {
                 Log.e("TTS", "TTS Failed")
             }
         }

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
            listView.adapter = adapter

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

        // WebView
        submitButton.setOnClickListener {
            val websiteName = websiteName.text.toString()

            Intent(this@MainActivity, WebViewActivity::class.java).also {
                it.putExtra("websiteName", websiteName)
                startActivity(it)
            }
        }

        // Spinner
        val personNames = arrayOf<String?>("john","james","robert","rose","jack","musk")

        if (spinner!= null)
        {
            val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item, personNames)
            spinner.adapter=arrayAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        "Selected item " +""+ personNames[position],
                        Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }

        // Toggle Button
        toggleButton.setOnCheckedChangeListener{
                _, isChecked ->
            val msg = "Toggle Button is: " + if (isChecked) "On" else "Off"
            Toast.makeText(
                this,
                msg,
                Toast.LENGTH_SHORT
            ).show()
        }

        // Radio Button
        submitButton.setOnClickListener {
            val name = editName.text.toString()
            val selected = radioGroup.checkedRadioButtonId
            var radioButton : RadioButton = findViewById(selected)
            if(radioButton.text == "Male")
                Toast.makeText(
                    this,
                    "Mr $name",
                    Toast.LENGTH_SHORT
                ).show()
            else
                Toast.makeText(
                    this,
                    "Ms $name",
                    Toast.LENGTH_SHORT
                ).show()
        }

        // Write, Read
        saveButton.setOnClickListener(View.OnClickListener {
            val file:String = fileNameEditText.text.toString()
            val data:String = fileDataTextView.text.toString()
            val fileOutputStream : FileOutputStream
            try {
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            } catch (e: FileNotFoundException){
                e.printStackTrace()
            }catch (e: NumberFormatException){
                e.printStackTrace()
            }catch (e: IOException){
                e.printStackTrace()
            }catch (e: Exception){
                e.printStackTrace()
            }
            Toast.makeText(applicationContext,"Your data has been saved!",Toast.LENGTH_LONG).show()
            fileNameEditText.text.clear()
            fileDataEditText.text.clear()
        })

        viewButton.setOnClickListener(View.OnClickListener {
            val filename = fileNameEditText.text.toString()
            if(filename.trim()!=""){
                var fileInputStream: FileInputStream? = null
                fileInputStream = openFileInput(filename)
                var inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while (run {
                        text = bufferedReader.readLine()
                        text
                    } != null) {
                    stringBuilder.append(text)
                }
                fileDataTextView.setText(stringBuilder.toString()).toString()
            }else{
                Toast.makeText(applicationContext,"File name cannot be empty!",Toast.LENGTH_LONG).show()
            }
        })

        // Implicit Intent
        shareButton.setOnClickListener{
            val msg:String = implicitEditText.text.toString()
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,msg)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share via"))
        }

    }
}