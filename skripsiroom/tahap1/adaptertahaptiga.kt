package com.example.skripsiroom.tahap1

import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.Data
import com.example.skripsiroom.ROOM.DataDatabase
import com.example.skripsiroom.ROOM.DataKesukaan
import com.example.skripsiroom.menu.menuutama
import kotlinx.android.synthetic.main.tahaptigarecview.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class adaptertahaptiga (private val u : ArrayList<Data>) : RecyclerView.Adapter<adaptertahaptiga.userViewHolder>() {
    val sharedPrefName = "Login"
    val keyData = "loggeduser"
    lateinit var sharedPref: SharedPreferences
    inner class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvtulisantahaptiga: TextView = itemView.tulisantahaptiga
        var ivgambartahaptiga: ImageView = itemView.gambartahaptiga
        var btnsimpan : Button = itemView.Simpandata
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptertahaptiga.userViewHolder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.tahaptigarecview, parent, false)
        return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: adaptertahaptiga.userViewHolder, position: Int) {
        var dataUser = u[position]
        holder.tvtulisantahaptiga.setText(dataUser.Nama)
        Glide.with(holder.itemView.context).load(dataUser.Gambar).fitCenter().into(holder.ivgambartahaptiga)
        holder.btnsimpan.setOnClickListener {
            Toast.makeText(holder.itemView.context,"masuk",Toast.LENGTH_SHORT).show()
            val data = Data(
                    0,
                    dataUser.Nama,
                    dataUser.Gambar,
                    dataUser.Kategori
            )
            arroom.add(data)

            sharedPref = holder.itemView.context.getSharedPreferences(sharedPrefName,0)
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putString(keyData, arroom.toString())
            editor.apply()

            CoroutineScope(Dispatchers.IO).launch {
                val DB by lazy { DataDatabase(holder.itemView.context) }
                DB.DataDao().tambahdata1(
                        DataKesukaan(
                                1,
                                dataUser.Nama,
                                dataUser.Gambar,
                                dataUser.Kategori)
                        )
            }
            Toast.makeText(holder.itemView.context,"Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, menuutama::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return u.size
    }

    fun isiData2(list: List<Data>?) {
        u.clear()
        if (list != null) {
            u.addAll(list)
        }
        notifyDataSetChanged()
    }
}