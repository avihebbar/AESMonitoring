package com.aapavani.aesmonitoring.data.models

import android.os.Parcel
import android.os.Parcelable

class FormItem(
    val name: String?,
    val display_text: String?,
    val type: String?,
    val values: ArrayList<String>?,
    val required: Boolean
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(display_text)
        parcel.writeString(type)
        parcel.writeStringList(values)
        parcel.writeByte(if (required) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FormItem> {
        var TEXT = "TEXT"
        var IMAGE = "IMAGE"
        var LARGE_TEXT = "LARGE_TEXT"
        var INT = "INT"
        var SELECT = "SELECT"

        override fun createFromParcel(parcel: Parcel): FormItem {
            return FormItem(parcel)
        }

        override fun newArray(size: Int): Array<FormItem?> {
            return arrayOfNulls(size)
        }
    }
}