package com.example.skripsiroom

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.skripsiroom.ROOM.Data
import com.example.skripsiroom.ROOM.DataDatabase
import com.example.skripsiroom.ROOM.roomadapter
import com.example.skripsiroom.menu.menuutama
import kotlinx.android.synthetic.main.activity_tambah.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException

class TambahActivity : AppCompatActivity() {
    val DB by lazy { DataDatabase(this) }
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

    lateinit var roomadapter: roomadapter
    private var idUser: Int = 0
    private var addEdit: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah)

        roomadapter = roomadapter(arrayListOf())
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        val spinner: Spinner = findViewById(R.id.kategori)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.kategorithp1,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        plhgbr.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        tbhgbr.setOnClickListener {
            val image = (imageView2.getDrawable() as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val imageInByte: ByteArray = stream.toByteArray()

            CoroutineScope(Dispatchers.IO).launch {
                DB.DataDao().tambahData(
                    Data(0, inputnama.text.toString(), imageInByte, kategori.selectedItem.toString())
                )
            }
            startActivity(Intent(this, menuutama::class.java))
            Log.d("masuk", "berhasil ditambahkan")
            Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_LONG).show()

        }

        idUser = intent.getIntExtra("idUser",0)
        addEdit = intent.getIntExtra("addEdit", 0)
        if (addEdit==0){
            tbhgbr.visibility = View.VISIBLE
            updategbr.visibility = View.GONE
        }
        else{
            tbhgbr.visibility = View.GONE
            updategbr.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                val useritem = DB.DataDao().getData1(idUser)
                inputnama.setText(useritem.Nama)
                
            }
        }

        updategbr.setOnClickListener {
            val image = (imageView2.getDrawable() as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val imageInByte: ByteArray = stream.toByteArray()
            CoroutineScope(Dispatchers.IO).launch {
                DB.DataDao().updateUser(
                    inputnama.text.toString(), imageInByte, kategori.selectedItem.toString(), idUser
                )
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val user  = DB.DataDao().getAllData()
            Log.d("abc", user.toString())
            withContext(Dispatchers.Main){
                roomadapter.isiData(user)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView2.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
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