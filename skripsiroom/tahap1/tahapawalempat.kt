package com.example.skripsiroom.tahap1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.DataDatabase
import com.example.skripsiroom.menu.menuutama
import kotlinx.android.synthetic.main.activity_tahapawalempat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*



class tahapawalempat : AppCompatActivity() {
    lateinit var mTTS : TextToSpeech
    lateinit var sharedPreferences: SharedPreferences
    val sharedPrefName = "Login"
    val keyData = "loggeduser"
    lateinit var adaptertahapempat: adaptertahapempat
    val DB by lazy { DataDatabase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahapawalempat)
        if(getSharedPreferences(sharedPrefName, MODE_PRIVATE).contains(keyData)){
            sharedPreferences = getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        }
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                var result = mTTS.setLanguage(Locale("id","ID"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported")
                    val installIntent = Intent()
                    installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                    startActivity(installIntent)
                }
            } else {
                Log.d("MTTS", "Local")
            }
        })

        adaptertahapempat = adaptertahapempat(arrayListOf(),mTTS)
        recviewtahap4.layoutManager = LinearLayoutManager(this)
        recviewtahap4.adapter = adaptertahapempat
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val user  = DB.DataDao().getAllData1()
            Log.d("abc", user.toString())
            withContext(Dispatchers.Main){
                adaptertahapempat.isiData(user)
            }
        }
    }
    override fun onOptionsItemSelected(menu: MenuItem): Boolean {
        return when (menu.getItemId()) {
            android.R.id.home -> {
                startActivity(Intent(this, menuutama::class.java))
                true
            }
            else -> super.onOptionsItemSelected(menu)
        }
    }

}