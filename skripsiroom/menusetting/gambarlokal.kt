package com.example.skripsiroom.menusetting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.Data
import com.example.skripsiroom.ROOM.DataDatabase
import kotlinx.android.synthetic.main.activity_gambarlokal.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class gambarlokal : AppCompatActivity(), adaptergambarlokal.RecyclerViewClickListener {
    val DB by lazy { DataDatabase(this) }
    lateinit var adaptergambarlokal: adaptergambarlokal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gambarlokal)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        adaptergambarlokal = adaptergambarlokal(arrayListOf())
        recviewlokal.layoutManager = LinearLayoutManager(this)
        recviewlokal.adapter = adaptergambarlokal
        adaptergambarlokal.listener = this

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val user  = DB.DataDao().getAllData()
            Log.d("abc", user.toString())
            withContext(Dispatchers.Main){
                adaptergambarlokal.isiData(user)
            }
        }
    }

    override fun buttonKlik(view: View, dataData : Data) {
        CoroutineScope(Dispatchers.IO).launch {
            DB.DataDao().deleteData(dataData)
            Log.d("hapus", "Berhasil dihapus")
        }
        CoroutineScope(Dispatchers.IO).launch {
            val user = DB.DataDao().getAllData()
            Log.d("abc", user.toString())
            withContext(Dispatchers.Main){
                adaptergambarlokal.isiData(user)
            }
        }
    }
    override fun onOptionsItemSelected(menu: MenuItem): Boolean {
        return when (menu.getItemId()) {
            android.R.id.home -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(menu)
        }
    }

}