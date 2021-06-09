package com.example.skripsiroom.tahap1

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.DataDatabase
import com.example.skripsiroom.ROOM.roomadapter
import com.example.skripsiroom.TambahActivity
import com.example.skripsiroom.data.gambar
import com.example.skripsiroom.menu.menuutama
import com.example.skripsiroom.menusetting.SettingActivity
import kotlinx.android.synthetic.main.activity_tahapawalkedua.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var kategoris : String? = ""
class tahapawalkedua : AppCompatActivity() {
    companion object {
        val extraText = "extratext"
    }
    var dataTerima : String? = ""
    lateinit var adaptertahapawalkedua: adaptertahapawalkedua
    lateinit var roomadapter: roomadapter
    private var arData = arrayListOf<gambar>()
    val DB by lazy { DataDatabase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahapawalkedua)

        dataTerima = intent.getStringExtra(extraText)
        var dtgambartahapkedua : TypedArray
        var dttahapkedua : Array<String>

        if (dataTerima.toString().equals("Mainan")){
            kategoris = "Mainan"
            dttahapkedua = resources.getStringArray(R.array.Mainan_text)
            dtgambartahapkedua = resources.obtainTypedArray(R.array.Mainan)
            for (position in dttahapkedua.indices){
                val data = gambar(
                        dtgambartahapkedua.getResourceId(position, -1),
                        dttahapkedua[position],
                )
                arData.add(data)
            }
            recviewtahapawalkedua.layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
            adaptertahapawalkedua = adaptertahapawalkedua(arData)
            roomadapter = roomadapter(arrayListOf())

            val concatAdapter = ConcatAdapter(adaptertahapawalkedua, roomadapter)
            recviewtahapawalkedua.adapter = concatAdapter
        }else if (dataTerima.toString().equals("Makanan")){
            kategoris = "Makanan"
            dttahapkedua = resources.getStringArray(R.array.Makanan_text)
            dtgambartahapkedua = resources.obtainTypedArray(R.array.Makanan)
            for (position in dttahapkedua.indices){
                val data = gambar(
                        dtgambartahapkedua.getResourceId(position, -1),
                        dttahapkedua[position],
                )
                arData.add(data)
            }
            recviewtahapawalkedua.layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
            adaptertahapawalkedua = adaptertahapawalkedua(arData)
            roomadapter = roomadapter(arrayListOf())

            val concatAdapter = ConcatAdapter(adaptertahapawalkedua, roomadapter)
            recviewtahapawalkedua.adapter = concatAdapter
        }else if (dataTerima.toString().equals("Minuman")){
            kategoris = "Minuman"
            dttahapkedua = resources.getStringArray(R.array.Minuman_text)
            dtgambartahapkedua = resources.obtainTypedArray(R.array.Minuman)
            for (position in dttahapkedua.indices){
                val data = gambar(
                        dtgambartahapkedua.getResourceId(position, -1),
                        dttahapkedua[position],
                )
                arData.add(data)
            }
            recviewtahapawalkedua.layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
            adaptertahapawalkedua = adaptertahapawalkedua(arData)
            roomadapter = roomadapter(arrayListOf())

            val concatAdapter = ConcatAdapter(adaptertahapawalkedua, roomadapter)
            recviewtahapawalkedua.adapter = concatAdapter
        }
        else if (dataTerima.toString().equals("Aktivitas")){
            kategoris = "Aktivitas"
            dttahapkedua = resources.getStringArray(R.array.KataKerja_text)
            dtgambartahapkedua = resources.obtainTypedArray(R.array.KataKerja)
            for (position in dttahapkedua.indices){
                val data = gambar(
                        dtgambartahapkedua.getResourceId(position, -1),
                        dttahapkedua[position],
                )
                arData.add(data)
            }
            recviewtahapawalkedua.layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
            adaptertahapawalkedua = adaptertahapawalkedua(arData)
            roomadapter = roomadapter(arrayListOf())

            val concatAdapter = ConcatAdapter(adaptertahapawalkedua, roomadapter)
            recviewtahapawalkedua.adapter = concatAdapter
        }

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            if (dataTerima.toString().equals("Mainan")){
                val user  = DB.DataDao().getData("Mainan")
                withContext(Dispatchers.Main){
                    roomadapter.isiData(user)
                }
            }else if (dataTerima.toString().equals("Makanan")){
                val user  = DB.DataDao().getData("Makanan")
                withContext(Dispatchers.Main){
                    roomadapter.isiData(user)
                }
            }else if (dataTerima.toString().equals("Minuman")){
                val user  = DB.DataDao().getData("Minuman")
                withContext(Dispatchers.Main){
                    roomadapter.isiData(user)
                }
            }
            else if (dataTerima.toString().equals("Aktivitas")){
                val user  = DB.DataDao().getData("Aktivitas")
                withContext(Dispatchers.Main){
                    roomadapter.isiData(user)
                }
            }

        }
    }

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