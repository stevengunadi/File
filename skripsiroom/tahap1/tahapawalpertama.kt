package com.example.skripsiroom.tahap1

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiroom.R
import com.example.skripsiroom.TambahActivity
import com.example.skripsiroom.menu.menuutama
import com.example.skripsiroom.menusetting.SettingActivity
import kotlinx.android.synthetic.main.activity_tahapawalpertama.*

class tahapawalpertama : AppCompatActivity() {
    private lateinit var dtkategori : Array<String>
    private lateinit var dtkategorigambar : TypedArray
    lateinit var adaptertahapawalpertama: adaptertahapawalpertama
    private var arData = arrayListOf<tahapawalpertamaa>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahapawalpertama)

        SiapkanDataawal()
        tambahdataawal()
        tampildataawal()
    }


    private fun SiapkanDataawal(){
        dtkategori = resources.getStringArray(R.array.kategorithp1)
        dtkategorigambar = resources.obtainTypedArray(R.array.kategorigbrthp1)
    }

    private fun tambahdataawal() {
        for (position in dtkategori.indices){
            val data = tahapawalpertamaa(
                    dtkategorigambar.getResourceId(position, -1),
                    dtkategori[position]

            )
            arData.add(data)
        }
    }

    private fun tampildataawal(){
        recviewatahapawal.layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        adaptertahapawalpertama = adaptertahapawalpertama(arData)
        recviewatahapawal.adapter = adaptertahapawalpertama
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