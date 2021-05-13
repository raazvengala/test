package com.example.test.data.api.model

import android.os.Parcel
import android.os.Parcelable

open class BaseEntity() : Parcelable{
    var Title:String? = null
    var Year:String? = null
    var imdbID:String? = null
    var Type:Type? = null
    var Poster:String? = null

    constructor(parcel: Parcel) : this() {
        Title = parcel.readString()
        Year = parcel.readString()
        imdbID = parcel.readString()
        parcel.readString()?.let {
            Type = com.example.test.data.api.model.Type.valueOf(it)
        }
        Poster = parcel.readString()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Title)
        parcel.writeString(Year)
        parcel.writeString(imdbID)
        parcel.writeString(Type?.toString())
        parcel.writeString(Poster)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseEntity> {
        override fun createFromParcel(parcel: Parcel): BaseEntity {
            return BaseEntity(parcel)
        }

        override fun newArray(size: Int): Array<BaseEntity?> {
            return arrayOfNulls(size)
        }
    }
}