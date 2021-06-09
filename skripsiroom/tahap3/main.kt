package com.example.skripsiroom.tahap3

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class main(
        var foto : Int = 0,
        var nama : String = "",
        var kategori : String = ""
) : Parcelable

