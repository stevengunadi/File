package com.example.skripsiroom.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsiroom.R
import com.example.skripsiroom.menusetting.SettingActivity
import com.example.skripsiroom.tahap1.tahapawalempat
import com.example.skripsiroom.tahap1.tahapawalpertama
import com.example.skripsiroom.tahap2.tahapduapertama
import com.example.skripsiroom.tahap3.komunikasi
import kotlinx.android.synthetic.main.activity_menuutama.*

class menuutama : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences
    val sharedPrefName = "Login"
    val keyData = "loggeduser"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuutama)
        btntahap1.setOnClickListener {
            if (getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE).contains(keyData)) {
                sharedPref = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
                startActivity(Intent(this, tahapawalempat::class.java))

            } else {
                startActivity(Intent(this, tahapawalpertama::class.java))
            }

        }
        btntahap2.setOnClickListener {
            startActivity(Intent(this, tahapduapertama::class.java))
        }
        btntahap3.setOnClickListener {
            startActivity(Intent(this, komunikasi::class.java))
        }
        btnsetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

    }
}