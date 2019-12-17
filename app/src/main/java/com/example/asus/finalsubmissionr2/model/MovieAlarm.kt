package com.example.asus.finalsubmissionr2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieAlarm (

    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("original_title")
    var title: String? = null,
    @SerializedName("overview")
    var desc: String? = null,
    @SerializedName("first_air_date")
    var date: String? = null,
    @SerializedName("poster_path")
    var img: String? = null

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(date)
        parcel.writeString(img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieAlarm> {
        override fun createFromParcel(parcel: Parcel): MovieAlarm {
            return MovieAlarm(parcel)
        }

        override fun newArray(size: Int): Array<MovieAlarm?> {
            return arrayOfNulls(size)
        }
    }
}