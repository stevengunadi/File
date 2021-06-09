package com.example.skripsiroom.tahap3

import android.content.Intent
import android.content.res.TypedArray
import android.graphics.BitmapFactory
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.Data
import com.example.skripsiroom.ROOM.DataDatabase
import com.example.skripsiroom.TambahActivity
import com.example.skripsiroom.menu.menuutama
import com.example.skripsiroom.menusetting.SettingActivity
import kotlinx.android.synthetic.main.activity_komunikasi.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.*

class komunikasi : AppCompatActivity() {
    lateinit var mTTS : TextToSpeech
    private var arTemp = arrayListOf<main>()
    var listkosong = arrayListOf<Data>()
    lateinit var adapterkomunikasi: adapterkomunikasi
    val DB by lazy { DataDatabase(this) }
    private lateinit var dtpredikatgambar : TypedArray
    private lateinit var dtpredikat : Array<String>
    private lateinit var dtobjekgambar : TypedArray
    private lateinit var dtobjek : Array<String>
    private lateinit var dtketerangangambar : TypedArray
    private lateinit var dtketerangan : Array<String>
    private lateinit var dtmakanangambar : TypedArray
    private lateinit var dtmakanan : Array<String>
    private lateinit var dtmainangambar : TypedArray
    private lateinit var dtmainan: Array<String>
    private lateinit var dtminumangambar : TypedArray
    private lateinit var dtminum: Array<String>
    var ktgr : String = ""
    var tulisan : String = ""
    var ktkrj : String = ""
    var drawableID: Int = 0
    lateinit  var gbruser : ByteArray
    var userid : Int = 0
    var isData : Boolean = false
    val randomGenerator = Random()
    var dataaa : MutableList<Data> = arrayListOf()
    var arraymakanan : MutableList<Data> = arrayListOf()
    var arrayminuman : MutableList<Data> = arrayListOf()
    var arraymainan : MutableList<Data> = arrayListOf()
    var arrayobjek : MutableList<Data> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_komunikasi)
        CoroutineScope(Dispatchers.IO).launch {
            val user1  = DB.DataDao().getdata4("Aktivitas")
            for (i in 0 .. user1.size-1){
                dataaa.add(Data(user1[i].id, user1[i].Nama, user1[i].Gambar, user1[i].Kategori))
            }
            val makanan =  DB.DataDao().getdata4("Makanan")
            for (i in 0 .. makanan.size-1){
                arraymakanan.add(Data(makanan[i].id, makanan[i].Nama, makanan[i].Gambar, makanan[i].Kategori))
            }
            val minumanan =  DB.DataDao().getdata4("Minuman")
            for (i in 0 .. minumanan.size-1){
                arrayminuman.add(Data(minumanan[i].id, minumanan[i].Nama, minumanan[i].Gambar, minumanan[i].Kategori))
            }
            val mainan =  DB.DataDao().getdata4("Mainan")
            for (i in 0 .. mainan.size-1){
                arraymainan.add(Data(mainan[i].id, mainan[i].Nama, mainan[i].Gambar, mainan[i].Kategori))
            }
            val objek =  DB.DataDao().getobjek()
            for (i in 0 .. objek.size-1){
                arrayobjek.add(Data(objek[i].id, objek[i].Nama, objek[i].Gambar, objek[i].Kategori))
            }
        }

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
        siapkandata()
        tampildata()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    fun siapkandata(){
        dtpredikat = resources.getStringArray(R.array.predikat_text)
        dtpredikatgambar = resources.obtainTypedArray(R.array.predikat)
        dtobjek = resources.getStringArray(R.array.objek_text)
        dtobjekgambar = resources.obtainTypedArray(R.array.Objek)
        dtketerangan = resources.getStringArray(R.array.keterangan_text)
        dtketerangangambar = resources.obtainTypedArray(R.array.keterangan)
        dtmakanan = resources.getStringArray(R.array.Makanan_text)
        dtmakanangambar = resources.obtainTypedArray(R.array.Makanan)
        dtmainan = resources.getStringArray(R.array.Mainan_text)
        dtmainangambar = resources.obtainTypedArray(R.array.Mainan)
        dtminum = resources.getStringArray(R.array.Minuman_text)
        dtminumangambar = resources.obtainTypedArray(R.array.Minuman)
    }

    fun tampildata() {

        var randomInt: Int = 0

        gambarmain.setImageResource(R.drawable.saya_mau)
        tulisanmain.setText("Saya mau")

        cardviewmain.setOnClickListener {
            mTTS.speak(tulisanmain.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
            if (ktgr.equals("")) {
                ktgr = "Sayaingin"
                Log.d("kategori", ktgr)
                arTemp.add(main(R.drawable.saya_mau, tulisanmain.text.toString(), ktgr))
                randomInt = randomGenerator.nextInt(dtpredikatgambar.length() + dataaa.size)
                if (randomInt < dtpredikatgambar.length()){
                    drawableID = dtpredikatgambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtpredikat[randomInt])
                    isData = false
                }else{
                    randomdata(ktgr)
                    isData = true
                }
            } else if (ktgr.equals("Sayaingin")) {
                ktgr = "predikat"
                tulisan = tulisanmain.text.toString()
                if (isData == true){
//                    aruser.add(Data(userid, tulisan, gbruser, ktgr))
                    arTemp.add(main(ByteBuffer.wrap(gbruser).getInt(), tulisan, ktgr))
                }else{
                    arTemp.add(main(drawableID, tulisan, ktgr))
                }
                if (tulisan.equals("makan")){
                    Log.v("stevencheck", "Masuk makan")
                    ktgr = "predikat"
                    ktkrj = "Makan"
                    randomInt = randomGenerator.nextInt(dtmakanangambar.length() + arraymakanan.size)
                    if (randomInt < dtmakanangambar.length()){
                        drawableID = dtmakanangambar.getResourceId(randomInt, -1)
                        gambarmain.setImageResource(drawableID)
                        tulisanmain.setText(dtmakanan[randomInt])
                        isData = false
                        ktgr = "predikat"
                    }else{
                        randompredikat(ktkrj)
                        isData = true
                        ktgr = "predikat"
                    }
                }else if (tulisan.equals("minum")){
                    Log.v("stevencheck", "Masuk Minum")
                    ktgr = "predikat"
                    ktkrj = "Minum"
                    randomInt = randomGenerator.nextInt(dtminumangambar.length() + arrayminuman.size)
                    if (randomInt < dtminumangambar.length()){
                        drawableID = dtminumangambar.getResourceId(randomInt, -1)
                        gambarmain.setImageResource(drawableID)
                        tulisanmain.setText(dtminum[randomInt])
                        isData = false
                        ktgr = "predikat"
                    }else{
                        randompredikat(ktkrj)
                        isData = true
                        ktgr = "predikat"
                    }
                } else if (tulisan.equals("bermain")){
                    Log.v("stevencheck", "Masuk Bermain")
                    ktgr = "predikat"
                    ktkrj = "Bermain"
                    randomInt = randomGenerator.nextInt(dtmainangambar.length() + arraymainan.size)
                    if (randomInt < dtmainangambar.length()){
                        drawableID = dtmainangambar.getResourceId(randomInt, -1)
                        gambarmain.setImageResource(drawableID)
                        tulisanmain.setText(dtmainan[randomInt])
                        isData = false
                        ktgr = "predikat"
                    }else{
                        randompredikat(ktkrj)
                        isData = true
                        ktgr = "predikat"
                    }
                } else if (tulisan.equals("menonton") || tulisan.equals("tidur") || tulisan.equals("berdoa")
                        ||tulisan.equals("duduk")||tulisan.equals("jalan")||tulisan.equals("masak")||tulisan.equals("menari")||tulisan.equals("menggambar") ){
                    Log.v("stevencheck", "Masuk Bebas")
                    ktgr = "objek"
                    randomInt = randomGenerator.nextInt(dtketerangangambar.length())
                    if (randomInt < dtketerangangambar.length()){
                        drawableID = dtketerangangambar.getResourceId(randomInt, -1)
                        gambarmain.setImageResource(drawableID)
                        tulisanmain.setText(dtketerangan[randomInt])
                        isData = false
                        ktgr = "objek"
                    }
                }
                else{
                    ktgr = "predikat"
                    Log.v("stevencheck", "Masuk kosong")
                    randomInt = randomGenerator.nextInt(dtobjekgambar.length() + arrayobjek.size)
                    if (randomInt < dtobjekgambar.length()){
                        drawableID = dtobjekgambar.getResourceId(randomInt, -1)
                        gambarmain.setImageResource(drawableID)
                        tulisanmain.setText(dtobjek[randomInt])
                        isData = false
                    }else{
                        randomdata(ktgr)
                        isData = true
                    }
                }

            } else if (ktgr.equals("predikat")) {
                ktgr = "objek"
                tulisan = tulisanmain.text.toString()
                if (isData == true){
                    arTemp.add(main(ByteBuffer.wrap(gbruser).getInt(), tulisan, ktgr))
                }else{
                    arTemp.add(main(drawableID, tulisan, ktgr))
                }
                randomInt = randomGenerator.nextInt(dtketerangangambar.length())
                randomInt < dtketerangangambar.length()
                drawableID = dtketerangangambar.getResourceId(randomInt, -1)
                gambarmain.setImageResource(drawableID)
                tulisanmain.setText(dtketerangan[randomInt])
            }
            else if (ktgr.equals("objek")){
                ktgr = "Keterangan"
                tulisan = tulisanmain.text.toString()
                arTemp.add(main(drawableID, tulisan, ktgr))
            }

            adapterkomunikasi = adapterkomunikasi(arTemp, mTTS)

            Log.d("masuk", arTemp.toString())
            rvtahapmain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvtahapmain.adapter = adapterkomunikasi

        }
        nextbtn.setOnClickListener {
            if (ktgr.equals("Sayaingin")){
                randomInt= randomGenerator.nextInt(dtpredikatgambar.length() + dataaa.size)
                Log.d("random1", getcountbykategori("Aktivitas").toString())
                Log.d("random", dtpredikatgambar.length().toString())
                if (randomInt < dtpredikatgambar.length()){
                    drawableID = dtpredikatgambar.getResourceId(randomInt, -1)
                    gambarmain.setImageResource(drawableID)
                    tulisanmain.setText(dtpredikat[randomInt])
                    isData = false
                }else{
                    randomdata("Aktivitas")
                    isData = true
                }
            } else if(ktgr.equals("predikat")){
                    if (tulisan.equals("makan")){
                        Log.v("stevencheck", "Masuk next Makan")
                        ktkrj = "Makan"
                        randomInt = randomGenerator.nextInt(dtmakanangambar.length() + arraymakanan.size)
                        if (randomInt < dtmakanangambar.length()){
                            drawableID = dtmakanangambar.getResourceId(randomInt, -1)
                            gambarmain.setImageResource(drawableID)
                            tulisanmain.setText(dtmakanan[randomInt])
                            isData = false
                        }else{
                            randompredikat(ktkrj)
                            isData = true
                            ktkrj = "Makan"
                        }
                    }else if (tulisan.equals("minum")){
                        Log.v("stevencheck", "Masuk next Minum")
                        ktkrj = "Minum"
                        randomInt = randomGenerator.nextInt(dtminumangambar.length() + arrayminuman.size)
                        if (randomInt < dtminumangambar.length()){
                            drawableID = dtminumangambar.getResourceId(randomInt, -1)
                            gambarmain.setImageResource(drawableID)
                            tulisanmain.setText(dtminum[randomInt])
                            isData = false
                        }else{
                            randompredikat(ktkrj)
                            isData = true
                            ktkrj = "Minum"
                        }
                    } else if (tulisan.equals("bermain")){
                        Log.v("stevencheck", "Masuk next Bermain")
                        ktkrj = "Bermain"
                        randomInt = randomGenerator.nextInt(dtmainangambar.length() + arraymainan.size)
                        if (randomInt < dtmainangambar.length()){
                            drawableID = dtmainangambar.getResourceId(randomInt, -1)
                            gambarmain.setImageResource(drawableID)
                            tulisanmain.setText(dtmainan[randomInt])
                            isData = false
                        }else{
                            randompredikat(ktkrj)
                            isData = true
                            ktkrj = "Bermain"
                        }
                    }
                    else{
                        Log.v("stevencheck", "Masuk next Kosong")
                        randomInt= randomGenerator.nextInt(dtobjekgambar.length() + arrayobjek.size)
                        if (randomInt < dtobjekgambar.length()){
                            drawableID = dtobjekgambar.getResourceId(randomInt, -1)
                            gambarmain.setImageResource(drawableID)
                            tulisanmain.setText(dtobjek[randomInt])
                            isData = false
                        }else{
                            randomdata(ktgr)
                            isData = true
                        }
                    }
            }else if (ktgr.equals("objek")){
                randomInt = randomGenerator.nextInt(dtketerangangambar.length())
                randomInt < dtketerangangambar.length()
                drawableID = dtketerangangambar.getResourceId(randomInt, -1)
                gambarmain.setImageResource(drawableID)
                tulisanmain.setText(dtketerangan[randomInt])
            }
        }


        deletebtn.setOnClickListener {
            adapterkomunikasi.removedatadrawable()
            Log.v("stevencheck", "data berhasil dihapus")

            ktgr = ""
            gambarmain.setImageResource(R.drawable.saya_mau)
            tulisanmain.setText("Saya mau")
        }


    }
    fun randomdata(jenis: String) {
        if (jenis == "Sayaingin"){
            Log.v("stevencheck", dataaa.toString())
            val user = dataaa[randomGenerator.nextInt(dataaa.size)]
            val imgViewer: ImageView = findViewById<View>(R.id.gambarmain) as ImageView
            val bitmap = BitmapFactory.decodeByteArray(user.Gambar, 0, user.Gambar.size)
            imgViewer.setImageBitmap(bitmap)
            tulisanmain.setText(user.Nama)
            userid = user.id
            gbruser = user.Gambar
            tulisan = tulisanmain.text.toString()
            isData = true
            val listkosong = arrayListOf<Data>()
            listkosong.add(user)
            Log.d("abc", user.toString())
        }
        else if (jenis == "predikat"){
            val user = arrayobjek[randomGenerator.nextInt(arrayobjek.size)]
            val imgViewer: ImageView = findViewById<View>(R.id.gambarmain) as ImageView
            val bitmap = BitmapFactory.decodeByteArray(user.Gambar, 0, user.Gambar.size)
            imgViewer.setImageBitmap(bitmap)
            tulisanmain.setText(user.Nama)
            userid = user.id
            gbruser = user.Gambar
            tulisan = tulisanmain.text.toString()
            isData = true
            listkosong.add(user)
            Log.d("abc", user.toString())
        }

    }

    fun randompredikat(katakerja: String){
        if (katakerja == "Makan"){
            Log.v("stevencheck", arraymakanan.toString())
            val user = arraymakanan[randomGenerator.nextInt(arraymakanan.size)]
            val imgViewer: ImageView = findViewById<View>(R.id.gambarmain) as ImageView
            val bitmap = BitmapFactory.decodeByteArray(user.Gambar, 0, user.Gambar.size)
            imgViewer.setImageBitmap(bitmap)
            tulisanmain.setText(user.Nama)
            userid = user.id
            gbruser = user.Gambar
            tulisan = tulisanmain.text.toString()
            isData = true
            ktkrj = "Makan"
            val listkosong = arrayListOf<Data>()
            listkosong.add(user)
            Log.d("abc", user.toString())
        }
        else if (katakerja == "Minum"){
            val user = arrayminuman[randomGenerator.nextInt(arrayminuman.size)]
            val imgViewer: ImageView = findViewById<View>(R.id.gambarmain) as ImageView
            val bitmap = BitmapFactory.decodeByteArray(user.Gambar, 0, user.Gambar.size)
            imgViewer.setImageBitmap(bitmap)
            tulisanmain.setText(user.Nama)
            userid = user.id
            gbruser = user.Gambar
            tulisan = tulisanmain.text.toString()
            isData = true
            ktkrj = "Minum"
            listkosong.add(user)
            Log.d("abc", user.toString())
        }
        else if (katakerja == "Bermain"){
            val user = arraymainan[randomGenerator.nextInt(arraymainan.size)]
            val imgViewer: ImageView = findViewById<View>(R.id.gambarmain) as ImageView
            val bitmap = BitmapFactory.decodeByteArray(user.Gambar, 0, user.Gambar.size)
            imgViewer.setImageBitmap(bitmap)
            tulisanmain.setText(user.Nama)
            userid = user.id
            gbruser = user.Gambar
            tulisan = tulisanmain.text.toString()
            isData = true
            ktkrj = "Bermain"
            listkosong.add(user)
            Log.d("abc", user.toString())
        }
    }
    fun getcountbykategori(kategori: String) : Int{
        var getcount : Int= 0
        CoroutineScope(Dispatchers.IO).launch {
            getcount  = DB.DataDao().getCountData(kategori)
        }
        return getcount
    }

//    fun convertbytetoint(convertgambar : ByteArray){
//        ret = IntArray(convertgambar.size)
//        for (i in 0 until convertgambar.size) {
//            ret[i] = convertgambar.get(i) and 0xff // Range 0 to 255, not -128 to 127
//        }
//        return ret
//    }

    //untuk membuat icon menu di navbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }
    //untuk action menu di navbar
    override fun onOptionsItemSelected(menu: MenuItem): Boolean {
        if (menu.getItemId() == R.id.pengaturan) {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        else if (menu.getItemId() == R.id.tambahgambar) {
            startActivity(Intent(this, TambahActivity::class.java))
        }
        else if(menu.getItemId()== android.R.id.home){
            startActivity(Intent(this, menuutama::class.java))
        }
        return true
    }
}