package com.example.recyclerview.database

import androidx.room.TypeConverter
import java.util.*

class ContactTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate(milliSecond: Long?): Date?{
        return milliSecond?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

}