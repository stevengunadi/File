package com.example.skripsiroom.menusetting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skripsiroom.R
import com.example.skripsiroom.ROOM.DataKesukaan
import kotlinx.android.synthetic.main.settinggambar.view.*

class adaptergambarfavorite (private val fav : ArrayList<DataKesukaan>) : RecyclerView.Adapter<adaptergambarfavorite.gambarViewHolder>() {

    var listener : RecyclerViewClickListener?=null

    interface RecyclerViewClickListener{
        fun buttonKlik(view: View, dataData: DataKesukaan)
    }

    inner class gambarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvNama : TextView = itemView.namatampilfavorite
        var tvGambar : ImageView = itemView.gambartampilfavorite
        var ivDelete : ImageView = itemView.tvdelete
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adaptergambarfavorite.gambarViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.settinggambarfavorite, parent, false)
        return gambarViewHolder(view)
    }

    override fun onBindViewHolder(holder: adaptergambarfavorite.gambarViewHolder, position: Int) {
        val datagambar1 = fav[position]
        holder.tvNama.setText(datagambar1.Nama)
        Glide.with(holder.itemView.context).load(datagambar1.Gambar).fitCenter().into(holder.tvGambar)

        holder.ivDelete.setOnClickListener {
            listener?.buttonKlik(it, datagambar1)
        }
    }

    override fun getItemCount(): Int {
        return fav.size
    }

    fun isiData(list: List<DataKesukaan>) {
        fav.clear()
        fav.addAll(list)
        notifyDataSetChanged()
    }
}