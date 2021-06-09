package com.example.skripsiroom.tahap2

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.BitmapFactory
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.Data
import com.example.skripsiroom.ROOM.DataDatabase
import com.example.skripsiroom.ROOM.DataKesukaan
import com.example.skripsiroom.menu.menuutama
import kotlinx.android.synthetic.main.activity_komunikasi.*
import kotlinx.android.synthetic.main.activity_tahapduapertama.*
import kotlinx.android.synthetic.main.activity_tahapduapertama.cardviewmain
import kotlinx.android.synthetic.main.activity_tahapduapertama.gambarmain
import kotlinx.android.synthetic.main.activity_tahapduapertama.tulisanmain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class tahapduapertama : AppCompatActivity() {
    lateinit var mTTS : TextToSpeech
    lateinit var dtnama : Array<String>
    lateinit var dtgambar : TypedArray
    lateinit var dtmainan : Array<String>
    lateinit var dtmainangambar : TypedArray
    lateinit var dtmakanan : Array<String>
    lateinit var dtmakanangambar : TypedArray
    lateinit var dtaktifitas : Array<String>
    lateinit var dtaktifitasgambar : TypedArray
    lateinit var dtminum : Array<String>
    lateinit var dtminumgambar : TypedArray
    val DB by lazy { DataDatabase(this) }
    var arrfavorite : MutableList<DataKesukaan> = arrayListOf()
    val random = Random()
    var kgr : String = ""
    var ktgr : String = ""
    val randomGenerator = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahapduapertama)

        CoroutineScope(Dispatchers.IO).launch {
            val user  = DB.DataDao().getiddatakesukaan(1)
            for (i in 0..user.size-1){
                arrfavorite.add(DataKesukaan(user[i].id,user[i].Nama,user[i].Gambar,user[i].kategori))
            }
            Log.d("arrayfavorite", arrfavorite.toString())
//            withContext(Dispatchers.Main){
//                tahapduaadapter.isiData(user)
//            }
        }

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                var result = mTTS.setLanguage(Locale("id", "ID"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported")
                    val installIntent = Intent()
                    installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                    startActivity(installIntent)
                }
            } else {
                Log.d("masuk3", "Local")
            }
        })
        siapdata()
        getdata()
        tampildata()

    }

    fun siapdata(){
        dtnama = resources.getStringArray(R.array.gabungantext)
        dtgambar = resources.obtainTypedArray(R.array.gabungangambar)
        dtmainan = resources.getStringArray(R.array.Mainan_text)
        dtmainangambar = resources.obtainTypedArray(R.array.Mainan)
        dtmakanan = resources.getStringArray(R.array.Makanan_text)
        dtmakanangambar = resources.obtainTypedArray(R.array.Makanan)
        dtaktifitas = resources.getStringArray(R.array.KataKerja_text)
        dtaktifitasgambar = resources.obtainTypedArray(R.array.KataKerja)
        dtminum = resources.getStringArray(R.array.Minuman_text)
        dtminumgambar = resources.obtainTypedArray(R.array.Minuman)
    }
    fun tampildata(){
        getdata()
        if (kgr.equals("Mainan")){
            ktgr = "Mainan"
            val randomInt = random.nextInt(dtmainangambar.length())
            val drawableID: Int = dtmainangambar.getResourceId(randomInt, -1)
            gambarmain.setImageResource(drawableID)
            tulisanmain.setText(dtmainan[randomInt])
            cardviewmain.setOnClickListener{
                ktgr = "Mainan"
                if (tulisanmain.text.toString() == tulisanmain2.text.toString()){
                    Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
                    mTTS.speak("Kamu Benar", TextToSpeech.QUEUE_FLUSH, null)
                    val randomInt = random.nextInt(dtmainangambar.length())
                    val drawableID: Int = dtmainangambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtmainan[randomInt])
                }else{
                    Toast.makeText(this, "Salah", Toast.LENGTH_SHORT).show()
                    mTTS.speak("Kamu salah pilih", TextToSpeech.QUEUE_FLUSH, null)
                    val randomInt = random.nextInt(dtmainangambar.length())
                    val drawableID: Int = dtmainangambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtmainan[randomInt])
                }

            }
        }else if (kgr.equals("Makanan")){
            ktgr = "Makanan"
            val randomInt = random.nextInt(dtmakanangambar.length())
            val drawableID: Int = dtmakanangambar.getResourceId(randomInt, -1)
            gambarmain.setImageResource(drawableID)
            tulisanmain.setText(dtmakanan[randomInt])
            cardviewmain.setOnClickListener{
                ktgr = "Makanan"
                if (tulisanmain.text.toString() == tulisanmain2.text.toString()){
                    Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
                    mTTS.speak("Kamu Benar", TextToSpeech.QUEUE_FLUSH, null)
                    val randomInt = random.nextInt(dtmakanangambar.length())
                    val drawableID: Int = dtmakanangambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtmakanan[randomInt])
                }else{
                    Toast.makeText(this, "Salah", Toast.LENGTH_SHORT).show()
                    mTTS.speak("Kamu salah pilih", TextToSpeech.QUEUE_FLUSH, null)
                    val randomInt = random.nextInt(dtmakanangambar.length())
                    val drawableID: Int = dtmakanangambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtmakanan[randomInt])
                }

            }
        }
        else if (kgr.equals("Aktivitas")){
            ktgr = "Aktivitas"
            val randomInt = random.nextInt(dtaktifitasgambar.length())
            val drawableID: Int = dtaktifitasgambar.getResourceId(randomInt, -1)
            gambarmain.setImageResource(drawableID)
            tulisanmain.setText(dtaktifitas[randomInt])
            cardviewmain.setOnClickListener{
                ktgr = "Aktivitas"
                if (tulisanmain.text.toString() == tulisanmain2.text.toString()){
                    Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
                    mTTS.speak("Kamu Benar", TextToSpeech.QUEUE_FLUSH, null)
                    val randomInt = random.nextInt(dtaktifitasgambar.length())
                    val drawableID: Int = dtaktifitasgambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtaktifitas[randomInt])
                }else{
                    Toast.makeText(this, "Salah", Toast.LENGTH_SHORT).show()
                    mTTS.speak("Kamu salah pilih", TextToSpeech.QUEUE_FLUSH, null)
                    val randomInt = random.nextInt(dtaktifitasgambar.length())
                    val drawableID: Int = dtaktifitasgambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtaktifitas[randomInt])
                }

            }
        }
        else if (kgr.equals("Minuman")){
            ktgr = "Minuman"
            val randomInt = random.nextInt(dtminumgambar.length())
            val drawableID: Int = dtminumgambar.getResourceId(randomInt, -1)
            gambarmain.setImageResource(drawableID)
            tulisanmain.setText(dtminum[randomInt])
            cardviewmain.setOnClickListener{
                ktgr = "Minuman"
                if (tulisanmain.text.toString() == tulisanmain2.text.toString()){
                    Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
                    mTTS.speak("Kamu Benar", TextToSpeech.QUEUE_FLUSH, null)
                    val randomInt = random.nextInt(dtminumgambar.length())
                    val drawableID: Int = dtminumgambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtminum[randomInt])
                }else{
                    Toast.makeText(this, "Salah", Toast.LENGTH_SHORT).show()
                    mTTS.speak("Kamu salah pilih", TextToSpeech.QUEUE_FLUSH, null)
                    val randomInt = random.nextInt(dtminumgambar.length())
                    val drawableID: Int = dtminumgambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtminum[randomInt])
                }
            }
        }else{
                val randomInt = random.nextInt(dtgambar.length())
                val drawableID: Int = dtgambar.getResourceId(randomInt, -1)
                gambarmain.setImageResource(drawableID)
                tulisanmain.setText(dtnama[randomInt])
                cardviewmain.setOnClickListener{
                    if(tulisanmain.text.toString() == tulisanmain2.text.toString()){
                        Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
                        mTTS.speak("Kamu Benar", TextToSpeech.QUEUE_FLUSH, null)
                        val randomInt = random.nextInt(dtgambar.length())
                        val drawableID: Int = dtgambar.getResourceId(randomInt, -1)
                        gambarmain.setImageResource(drawableID)
                        tulisanmain.setText(dtnama[randomInt])
                    }else{
                        Toast.makeText(this, "Salah", Toast.LENGTH_SHORT).show()
                        mTTS.speak("Kamu salah pilih", TextToSpeech.QUEUE_FLUSH, null)
                        val randomInt = random.nextInt(dtgambar.length())
                        val drawableID: Int = dtgambar.getResourceId(randomInt, -1)
                        gambarmain.setImageResource(drawableID)
                        tulisanmain.setText(dtnama[randomInt])
                    }
                }
        }

    }

    fun getdata(){

        arrfavorite.forEach{
            val imgViewer: ImageView = findViewById<View>(R.id.gambarmain2) as ImageView
            val bitmap = BitmapFactory.decodeByteArray(it.Gambar, 0, it.Gambar.size)
            imgViewer.setImageBitmap(bitmap)
            tulisanmain2.setText(it.Nama)
            kgr = it.kategori
        }

        carviewmain2.setOnClickListener {
            Toast.makeText(this, "Benar", Toast.LENGTH_SHORT).show()
            mTTS.speak("Kamu Benar", TextToSpeech.QUEUE_FLUSH, null)
            if (kgr.equals("Makanan")){
                ktgr = "Makanan"
                val randomInt = random.nextInt(dtmakanangambar.length())
                val drawableID: Int = dtmakanangambar.getResourceId(randomInt, -1)
                gambarmain.setImageResource(drawableID)
                tulisanmain.setText(dtmakanan[randomInt])
            }else if (kgr.equals("Minuman")){
                ktgr = "Minuman"
                val randomInt = random.nextInt(dtminumgambar.length())
                val drawableID: Int = dtminumgambar.getResourceId(randomInt, -1)
                gambarmain.setImageResource(drawableID)
                tulisanmain.setText(dtminum[randomInt])
            }else if(kgr.equals("Aktivitas")){
                ktgr = "Aktivitas"
                val randomInt = random.nextInt(dtaktifitasgambar.length())
                val drawableID: Int = dtaktifitasgambar.getResourceId(randomInt, -1)
                gambarmain.setImageResource(drawableID)
                tulisanmain.setText(dtaktifitas[randomInt])
            }else if (kgr.equals("Mainan")){
                ktgr = "Mainan"
                val randomInt = random.nextInt(dtmainangambar.length())
                val drawableID: Int = dtmainangambar.getResourceId(randomInt, -1)
                gambarmain.setImageResource(drawableID)
                tulisanmain.setText(dtmainan[randomInt])
            }
        }
    }

    //untuk action menu di navbar
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

