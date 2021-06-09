package com.example.skripsiroom.tahap1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsiroom.R
import com.example.skripsiroom.data.gambar
import kotlinx.android.synthetic.main.imagelist.view.*


class adaptertahapawalkedua(private var isitahapawalkedua: ArrayList<gambar>) : RecyclerView.Adapter<adaptertahapawalkedua.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvtulisan: TextView = itemView.tulisanmain
        var tvgambar: ImageView = itemView.gambarmain

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptertahapawalkedua.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.imagelist, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adaptertahapawalkedua.ListViewHolder, position: Int) {
        var gambar = isitahapawalkedua[position]
        holder.tvtulisan.setText(gambar.nama)
        holder.tvgambar.setImageResource(gambar.foto)
        holder.itemView.setOnClickListener {
            val pIntent = Intent(holder.itemView.context, tahapawalketiga::class.java)
            pIntent.putExtra(tahapawalketiga.extraText, position.toString() )
            pIntent.putExtra("image", gambar.nama)
            pIntent.putExtra("kategori", kategoris.toString())
            ContextCompat.startActivity(holder.itemView.context, pIntent, null)

        }
    }

    override fun getItemCount(): Int {
        return isitahapawalkedua.size
    }
}

