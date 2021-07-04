package com.aapavani.aesmonitoring.data.models

import android.os.Parcel
import android.os.Parcelable

class Project(
    val id : String,
    val name : String,
    val location : AddressObject,
    val noOfPumps : Int,
    val pmIntervalInMonths : Int,
    val siteInchargeId : String

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(AddressObject::class.java.classLoader)!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeParcelable(location, flags)
        parcel.writeInt(noOfPumps)
        parcel.writeInt(pmIntervalInMonths)
        parcel.writeString(siteInchargeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Project> {
        override fun createFromParcel(parcel: Parcel): Project {
            return Project(parcel)
        }

        override fun newArray(size: Int): Array<Project?> {
            return arrayOfNulls(size)
        }
    }

}