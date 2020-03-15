package com.example.recyclerview.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recyclerview.models.Contact


@Database(entities = [Contact::class], version = 1)
@TypeConverters(ContactTypeConverters::class)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}