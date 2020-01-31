package com.example.recyclerview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Contact::class], version = 1)
abstract  class AppDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao



    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "contacts"
            ).allowMainThreadQueries()
                .build()
        }
    }


//    companion object{
//        @Volatile private var instance: AppDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
//            instance ?: buildDatabase(context).also { instance = it }
//        }
//
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
//            AppDatabase::class.java, "contacts").build()
//    }

}
