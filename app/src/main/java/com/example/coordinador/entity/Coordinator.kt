package com.example.coordinador.entity

import android.os.Parcel
import android.os.Parcelable

//Nota: Usar el add parcelable implementations para que no de error el this de la linea 14
class Coordinator (
    var idC : Int?,
    var nombre : String?,
    var apellido : String?,
    var fechaNac : String?,
    var titulo : String?,
    var email : String?,
    var facultad : String?
        ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idC!!)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(fechaNac)
        parcel.writeString(email)
        parcel.writeString(facultad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coordinator> {
        override fun createFromParcel(parcel: Parcel): Coordinator {
            return Coordinator(parcel)
        }

        override fun newArray(size: Int): Array<Coordinator?> {
            return arrayOfNulls(size)
        }
    }

}
