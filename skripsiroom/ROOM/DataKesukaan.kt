package com.example.skripsiroom.ROOM

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tab_DataKesukaan")

data class DataKesukaan(
        @PrimaryKey(autoGenerate = false) val id: Int,
        val Nama : String,
        val Gambar : ByteArray,
        val kategori : String
)
