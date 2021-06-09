package com.example.skripsiroom.tahap3

import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skripsiroom.R
import kotlinx.android.synthetic.main.tahapmain1.view.*

class adapterkomunikasi (private var main1 : ArrayList<main>,private val tts: TextToSpeech) : RecyclerView.Adapter<adapterkomunikasi.ListViewHolder>() {

    inner class ListViewHolder (Itemview : View) : RecyclerView.ViewHolder(Itemview){
        var tvnama : TextView = itemView.tulisantahapmain1
        var tvGambar : ImageView = itemView.gbrtahapmain1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterkomunikasi.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.tahapmain1, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterkomunikasi.ListViewHolder, position: Int) {
        var data = main1[position]
        holder.tvnama.setText(data.nama)
        Glide.with(holder.itemView.context).load(data.foto).fitCenter().into(holder.tvGambar)
        holder.itemView.setOnClickListener {
            tts.speak(data.nama, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    override fun getItemCount(): Int {
        return main1.size
    }
    fun removedatadrawable(){
        main1.clear()
        notifyDataSetChanged()
    }

}