package com.example.skripsiroom.tahap1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.Data
import com.example.skripsiroom.ROOM.DataDatabase
import com.example.skripsiroom.ROOM.DataKesukaan
import com.example.skripsiroom.data.gambar
import com.example.skripsiroom.menu.menuutama
import kotlinx.android.synthetic.main.activity_tahapawalketiga.*
import kotlinx.android.synthetic.main.tahaptigarecview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.sql.Blob

var arTemp = ArrayList<gambar>()
var arroom = ArrayList<Data>()
class tahapawalketiga : AppCompatActivity() {
    companion object {
        val extraText = "extratext"
        val image = "Image"
        val kategoris = "kategori"
        val extraGambar = "extragambar"
    }
    val sharedPrefName = "Login"
    val keyData = "loggeduser"
    lateinit var sharedPref: SharedPreferences
    lateinit var adaptertahaptiga: adaptertahaptiga
    var arroom = arrayListOf<Data>()
    val DB by lazy { DataDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahapawalketiga)
        adaptertahaptiga = adaptertahaptiga(arroom)
        Log.d("room", arroom.toString())
        if(getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE).contains(keyData)){
            sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
            Toast.makeText(this, sharedPref.getString(keyData, null), Toast.LENGTH_LONG).show()
            val intentMain = Intent(this, menuutama::class.java)
            startActivity(intentMain)
        }

        var dtgambartahapketiga : TypedArray
        var dataTerima : String? = intent.getStringExtra(extraText)
        var datakategori : String? = intent.getStringExtra(kategoris)
        val idgambar = dataTerima?.toInt()
        Log.d("coba1", dataTerima.toString())
        Log.d("coba2", datakategori.toString())
         if (datakategori.toString().equals("Mainan")){
            if(idgambar!=null){
                dtgambartahapketiga = resources.obtainTypedArray(R.array.Mainan)
                gbrtahaptigarec.setImageResource(dtgambartahapketiga.getResourceId(idgambar!!, -1))
                tulistahaptigarec.setText(intent.getStringExtra("image"))
                btnsimpan.setOnClickListener{
                    val image = (gbrtahaptigarec.getDrawable() as BitmapDrawable).bitmap
                    val stream = ByteArrayOutputStream()
                    image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val imageInByte: ByteArray = stream.toByteArray()
                    val data = gambar(
                            dtgambartahapketiga.getResourceId(idgambar!!, -1),
                            tulistahaptigarec.text.toString()
                    )
                    arTemp.add(data)

                    sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString(keyData, arTemp.toString())
                    editor.apply()


                    CoroutineScope(Dispatchers.IO).launch {
                        DB.DataDao().tambahdata1(
                                DataKesukaan(
                                        1,
                                        tulistahaptigarec.text.toString(),
                                        imageInByte,
                                        datakategori.toString())
                        )
                    }
                    Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, menuutama::class.java))
                }
            }else{
                Log.d("recview", "masuk")
                cviewtahaptiga.visibility= View.GONE
                btnsimpan.visibility = View.GONE
                recviewtahap3.visibility = View.VISIBLE
                recviewtahap3.layoutManager = LinearLayoutManager(this)
                recviewtahap3.adapter = adaptertahaptiga
            }
        }else if(datakategori.toString().equals("Makanan")){
            if (idgambar!=null){
                dtgambartahapketiga = resources.obtainTypedArray(R.array.Makanan)
                gbrtahaptigarec.setImageResource(dtgambartahapketiga.getResourceId(idgambar!!, -1))
                tulistahaptigarec.setText(intent.getStringExtra("image"))
                btnsimpan.setOnClickListener{
                    val image = (gbrtahaptigarec.getDrawable() as BitmapDrawable).bitmap
                    val stream = ByteArrayOutputStream()
                    image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val imageInByte: ByteArray = stream.toByteArray()
                    val data = gambar(
                            dtgambartahapketiga.getResourceId(idgambar!!, -1),
                            tulistahaptigarec.text.toString()
                    )
                    arTemp.add(data)

                    sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString(keyData, arTemp.toString())
                    editor.apply()


                    CoroutineScope(Dispatchers.IO).launch {
                        DB.DataDao().tambahdata1(
                                DataKesukaan(
                                        1,
                                        tulistahaptigarec.text.toString(),
                                        imageInByte,
                                        datakategori.toString())

                        )
                    }
                    Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, menuutama::class.java))
                }
            }else{
                Log.d("recview", "masuk")
                cviewtahaptiga.visibility= View.GONE
                btnsimpan.visibility = View.GONE
                recviewtahap3.visibility = View.VISIBLE
                recviewtahap3.layoutManager = LinearLayoutManager(this)
                recviewtahap3.adapter = adaptertahaptiga
            }
        }else if(datakategori.toString().equals("Minuman")){
             if (idgambar!=null){
                 dtgambartahapketiga = resources.obtainTypedArray(R.array.Minuman)
                 gbrtahaptigarec.setImageResource(dtgambartahapketiga.getResourceId(idgambar!!, -1))
                 tulistahaptigarec.setText(intent.getStringExtra("image"))
                 btnsimpan.setOnClickListener{
                     val image = (gbrtahaptigarec.getDrawable() as BitmapDrawable).bitmap
                     val stream = ByteArrayOutputStream()
                     image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                     val imageInByte: ByteArray = stream.toByteArray()
                     val data = gambar(
                             dtgambartahapketiga.getResourceId(idgambar!!, -1),
                             tulistahaptigarec.text.toString()
                     )
                     arTemp.add(data)

                     sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
                     val editor: SharedPreferences.Editor = sharedPref.edit()
                     editor.putString(keyData, arTemp.toString())
                     editor.apply()


                     CoroutineScope(Dispatchers.IO).launch {
                         DB.DataDao().tambahdata1(
                                 DataKesukaan(
                                         1,
                                         tulistahaptigarec.text.toString(),
                                         imageInByte,
                                         datakategori.toString())

                         )
                     }
                     Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                     startActivity(Intent(this, menuutama::class.java))
                 }
             }else{
                 Log.d("recview", "masuk")
                 cviewtahaptiga.visibility= View.GONE
                 btnsimpan.visibility = View.GONE
                 recviewtahap3.visibility = View.VISIBLE
                 recviewtahap3.layoutManager = LinearLayoutManager(this)
                 recviewtahap3.adapter = adaptertahaptiga
             }
         }
         else if(datakategori.toString().equals("Aktivitas")){
            if (idgambar!=null){
                dtgambartahapketiga = resources.obtainTypedArray(R.array.KataKerja)
                gbrtahaptigarec.setImageResource(dtgambartahapketiga.getResourceId(idgambar!!, -1))
                tulistahaptigarec.setText(intent.getStringExtra("image"))
                btnsimpan.setOnClickListener{
                    val image = (gbrtahaptigarec.getDrawable() as BitmapDrawable).bitmap
                    val stream = ByteArrayOutputStream()
                    image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val imageInByte: ByteArray = stream.toByteArray()
                    val data = gambar(
                            dtgambartahapketiga.getResourceId(idgambar!!, -1),
                            tulistahaptigarec.text.toString()
                    )
                    arTemp.add(data)

                    sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString(keyData, arTemp.toString())
                    editor.apply()

                    CoroutineScope(Dispatchers.IO).launch {
                        DB.DataDao().tambahdata1(
                                DataKesukaan(1, tulistahaptigarec.text.toString(), imageInByte,datakategori.toString())
                        )
                    }
                    Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, menuutama::class.java))
                }
            }else{
                Log.d("recview", "masuk")
                cviewtahaptiga.visibility= View.GONE
                gbrtahaptigarec.visibility = View.GONE
                btnsimpan.visibility = View.GONE
                recviewtahap3.visibility = View.VISIBLE
                recviewtahap3.layoutManager = LinearLayoutManager(this)
                recviewtahap3.adapter = adaptertahaptiga
            }

        }
    }
    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val user  = intent.getStringExtra("image")?.let { DB.DataDao().getData2(it) }
            Log.d("abc", user.toString())
            withContext(Dispatchers.Main){
                adaptertahaptiga.isiData2(user)
            }
        }
    }
}
