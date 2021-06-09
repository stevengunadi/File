package com.example.skripsiroom.menusetting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.skripsiroom.R
import com.example.skripsiroom.menu.menuutama
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        settingdatagambar.setOnClickListener {
            startActivity(Intent(this, gambarlokal::class.java))
        }
        settinggambarfavorite.setOnClickListener {
            startActivity(Intent(this, gambarfavoritee::class.java))
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