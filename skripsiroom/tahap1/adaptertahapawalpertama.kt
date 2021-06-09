package com.example.skripsiroom.tahap1

import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsiroom.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.imagelist.view.*

@Parcelize
data class tahapawalpertamaa(
        var foto : Int,
        var nama : String
):Parcelable

class adaptertahapawalpertama(private var isitahapawalpertama: ArrayList<tahapawalpertamaa>) : RecyclerView.Adapter<adaptertahapawalpertama.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvtulisan: TextView = itemView.tulisanmain
        var tvgambar: ImageView = itemView.gambarmain

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.imagelist, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adaptertahapawalpertama.ListViewHolder, position: Int) {
        var gambar = isitahapawalpertama[position]
        holder.tvtulisan.setText(gambar.nama)
        holder.tvgambar.setImageResource(gambar.foto)
        holder.tvgambar.setOnClickListener {
            val pIntent = Intent(holder.itemView.context, tahapawalkedua::class.java)
            pIntent.putExtra(tahapawalkedua.extraText, gambar.nama)
            ContextCompat.startActivity(holder.itemView.context, pIntent, null)
        }
    }

    override fun getItemCount(): Int {
        return isitahapawalpertama.size
    }
}