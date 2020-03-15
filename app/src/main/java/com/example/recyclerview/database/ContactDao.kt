package com.example.recyclerview.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recyclerview.models.Contact
import java.util.*


@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts_table")
    fun getContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts_table WHERE uid=(:id)")
    fun getContact(id: UUID): LiveData<Contact?>

    @Insert
    fun addContacts(vararg contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

}