package com.aapavani.aesmonitoring.data.models

import android.os.Parcel
import android.os.Parcelable

class AddressObject(
    val lat : Double,
    val lon : Double,
    val addressText : String
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
        parcel.writeString(addressText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddressObject> {
        override fun createFromParcel(parcel: Parcel): AddressObject {
            return AddressObject(parcel)
        }

        override fun newArray(size: Int): Array<AddressObject?> {
            return arrayOfNulls(size)
        }
    }

}
