package com.example.skripsiroom.ROOM

import androidx.room.*

@Dao
interface DataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun tambahData(data : Data)

    @Query("SELECT * FROM Tab_Data ORDER BY Nama ASC")
    suspend fun getAllData() : List<Data>

    @Delete
    suspend fun deleteData(data : Data)

    @Query("SELECT * FROM Tab_Data WHERE Kategori=:kategori")
    suspend fun getData(kategori : String) : List<Data>

    @Query("SELECT * FROM Tab_Data WHERE id=:Noid")
    suspend fun getData1(Noid: Int) : Data

    @Query("SELECT * FROM Tab_Data WHERE Nama=:nama")
    suspend fun getData2(nama : String) : List<Data>

    @Query("UPDATE Tab_Data SET Nama=:UserName, Gambar =:gbr, Kategori=:Password  WHERE id=:Noid")
    suspend fun updateUser(UserName : String, gbr : ByteArray, Password : String, Noid : Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun tambahdata1(dataKesukaan: DataKesukaan)

    @Query("SELECT * FROM Tab_DataKesukaan")
    suspend fun getAllData1() : List<DataKesukaan>

    @Query("SELECT * FROM Tab_DataKesukaan WHERE ID = :id")
    suspend fun getiddatakesukaan(id: Int) : List<DataKesukaan>

    @Delete
    suspend fun deleteData1(data : DataKesukaan)


    @Query("SELECT * FROM TAB_DATA WHERE Kategori=:kategori")
    suspend fun getdata4(kategori: String) : List<Data>

    @Query("SELECT COUNT(*) FROM TAB_DATA WHERE Kategori=:kategori")
    suspend fun getCountData(kategori: String) : Int

    @Query("SELECT * FROM TAB_DATA WHERE Kategori!='Aktivitas'")
    suspend fun getobjek() : List<Data>


}