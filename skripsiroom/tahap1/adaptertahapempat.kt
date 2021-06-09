package com.example.skripsiroom.tahap1

import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.DataKesukaan
import kotlinx.android.synthetic.main.imagelisttahap4.view.*
import kotlin.collections.ArrayList

class adaptertahapempat (private var favempat: ArrayList<DataKesukaan>,private val tts:TextToSpeech) : RecyclerView.Adapter<adaptertahapempat.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvtulisan: TextView = itemView.tulistahaptigarec
        var tvgambar: ImageView = itemView.gbrtahaptigarec

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptertahapempat.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.imagelisttahap4, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adaptertahapempat.ListViewHolder, position: Int) {
        var gambar = favempat[position]
        holder.tvtulisan.setText(gambar.Nama)
        Glide.with(holder.itemView.context).load(gambar.Gambar).fitCenter().into(holder.tvgambar)
        holder.itemView.setOnClickListener {
            tts.speak(favempat[position].Nama, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    override fun getItemCount(): Int {
        return favempat.size
    }
    fun isiData(list: List<DataKesukaan>) {
        favempat.clear()
        favempat.addAll(list)
        notifyDataSetChanged()
    }
}