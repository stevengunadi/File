package com.example.skripsiroom.menusetting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.DataDatabase
import com.example.skripsiroom.ROOM.DataKesukaan
import kotlinx.android.synthetic.main.activity_gambarfavoritee.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class gambarfavoritee : AppCompatActivity(), adaptergambarfavorite.RecyclerViewClickListener {
    lateinit var sharedPref: SharedPreferences
    val sharedPrefName = "Login"
    val keyData = "loggeduser"

    val DB by lazy { DataDatabase(this) }
    lateinit var adaptergambarfavorite: adaptergambarfavorite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gambarfavoritee)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        if(getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE).contains(keyData)){
            sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        }

        adaptergambarfavorite = adaptergambarfavorite(arrayListOf())
        recfavorite.layoutManager = LinearLayoutManager(this)
        recfavorite.adapter = adaptergambarfavorite
        adaptergambarfavorite.listener = this


    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val user  = DB.DataDao().getAllData1()
            Log.d("abc", user.toString())
            withContext(Dispatchers.Main){
                adaptergambarfavorite.isiData(user)
            }
        }
    }

    override fun buttonKlik(view: View, dataData: DataKesukaan) {
        CoroutineScope(Dispatchers.IO).launch {
            DB.DataDao().deleteData1(dataData)
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.clear()
            editor.commit()
        }
        CoroutineScope(Dispatchers.IO).launch {
            val user = DB.DataDao().getAllData1()
            Log.d("abc", user.toString())
            withContext(Dispatchers.Main){
                adaptergambarfavorite.isiData(user)
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