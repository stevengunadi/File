package com.example.skripsiroom.menusetting

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.Data
import com.example.skripsiroom.TambahActivity
import kotlinx.android.synthetic.main.settinggambar.view.*

class adaptergambarlokal (private val gbr : ArrayList<Data>) : RecyclerView.Adapter<adaptergambarlokal.gambarViewHolder>() {

    var listener : RecyclerViewClickListener?=null

    interface RecyclerViewClickListener{
        fun buttonKlik(view:View, dataData: Data)
    }

    inner class gambarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvNama : TextView = itemView.namatampilfavorite
        var tvKategori : TextView = itemView.kategoritampil
        var tvGambar : ImageView = itemView.gambartampilfavorite
        var ivEdit : ImageView = itemView.tvedit
        var ivDelete : ImageView = itemView.tvdelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptergambarlokal.gambarViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.settinggambar, parent, false)
        return gambarViewHolder(view)
    }

    override fun onBindViewHolder(holder: adaptergambarlokal.gambarViewHolder, position: Int) {
        val datagambar = gbr[position]
        holder.tvNama.setText(datagambar.Nama)
        holder.tvKategori.setText(datagambar.Kategori)
        Glide.with(holder.itemView.context).load(datagambar.Gambar).fitCenter().into(holder.tvGambar)

        holder.ivEdit.setOnClickListener {
            val intent = Intent(it.context, TambahActivity::class.java)
            intent.putExtra("idUser",datagambar.id)
            intent.putExtra("addEdit",1)
            it.context.startActivity(intent)
        }
        holder.ivDelete.setOnClickListener {
            listener?.buttonKlik(it, datagambar)
        }

    }

    override fun getItemCount(): Int {
        return gbr.size
    }

    fun isiData(list: List<Data>) {
        gbr.clear()
        gbr.addAll(list)
        notifyDataSetChanged()
    }


}