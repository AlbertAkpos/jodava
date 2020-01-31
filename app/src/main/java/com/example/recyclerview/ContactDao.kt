package com.example.recyclerview

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


/**
 *
 * The DAO is used to set up entities, that is responsible for
 * querying the data. It should have methods that manipulate the
 * data base.
 *
 */
@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<Contact>

    //Inserting one or more contacts
    @Insert
    fun insertAll(vararg contacts: Contact)


    //find by first name and last name
    @Query("SELECT * FROM contacts WHERE uid =:uid")
    fun getById(uid: Int): Contact


    //Delete user
    @Delete
    fun delete(contact: Contact)


}