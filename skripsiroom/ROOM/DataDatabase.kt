package com.example.skripsiroom.ROOM

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.webkit.CookieSyncManager.createInstance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skripsiroom.R
import kotlinx.android.synthetic.main.activity_tambah.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors


@Database(entities = arrayOf(Data::class, DataKesukaan::class), version = 1)

abstract class DataDatabase : RoomDatabase() {
    abstract fun DataDao() : DataDao
    companion object {
        private var INSTANCE: DataDatabase? = null
        private var LOCK = Any()
        operator fun invoke(context: Context) = INSTANCE ?:synchronized(LOCK){
            INSTANCE ?: buildDatabase(context).also{
                INSTANCE = it
            }
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                DataDatabase::class.java, "dbData.db"
        ).build()
    }
}
