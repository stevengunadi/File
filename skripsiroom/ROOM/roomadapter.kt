package com.example.skripsiroom.ROOM

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skripsiroom.R
import com.example.skripsiroom.tahap1.kategoris
import com.example.skripsiroom.tahap1.tahapawalketiga
import kotlinx.android.synthetic.main.imagelist.view.*
import kotlinx.android.synthetic.main.tahaptigarecview.view.*

class roomadapter (private val user : ArrayList<Data>) : RecyclerView.Adapter<roomadapter.userViewHolder>() {

    inner class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvtulisan: TextView = itemView.tulisanmain
        var ivgambar: ImageView = itemView.gambarmain

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
//        TODO("Not yet implemented")
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.imagelist, parent, false)
        return userViewHolder(view)
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return user.size
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
//        TODO("Not yet implemented")
        var dataUser = user[position]
        holder.tvtulisan.setText(dataUser.Nama)
        Glide.with(holder.itemView.context).load(dataUser.Gambar).fitCenter().into(holder.ivgambar)
        holder.itemView.setOnClickListener {
            val pIntent = Intent(holder.itemView.context, tahapawalketiga::class.java)
            pIntent.putExtra(tahapawalketiga.extraGambar, position.toString() )
            pIntent.putExtra("image", dataUser.Nama)
            pIntent.putExtra("kategori", kategoris.toString())
            ContextCompat.startActivity(holder.itemView.context, pIntent, null)


        }
    }

    fun isiData(list: List<Data>) {
        user.clear()
        user.addAll(list)
        notifyDataSetChanged()
    }

}