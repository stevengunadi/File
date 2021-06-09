package com.example.skripsiroom.ROOM

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tab_Data")

data class Data(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val Nama : String,
    val Gambar : ByteArray,
    val Kategori : String
)
