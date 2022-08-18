package com.example.albumnfts.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(

    val nombre: String,
    var id: String,
    val precio: String,
    val cantidad: String,
    val color: String,
    val URL: String?
) : Parcelable {
    constructor () :
            this("","","","","", "")
}
