package com.example.recyclerview

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "contacts")
@Parcelize
data class Contact(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email")var email: String,
    @ColumnInfo(name = "phone_number")var phone_number: String,
    @ColumnInfo(name = "image")var image: String?=null
): Parcelable