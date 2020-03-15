package com.example.recyclerview.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "contacts_table")
@Parcelize
data class Contact(
                   var uid: String?,
                   var name: String,
                   @PrimaryKey
                   var email: String,
                   var phone_number: String,
                   var image: String? ): Parcelable{

    constructor(): this("", "", "",  "","")
}