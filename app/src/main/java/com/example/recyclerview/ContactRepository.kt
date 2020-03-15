package com.example.recyclerview

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.recyclerview.database.ContactDatabase
import com.example.recyclerview.models.Contact
import java.util.*

private const val DATABASE_NAME = "contact-database"

class ContactRepository private constructor(context: Context){

    private val database: ContactDatabase = Room.databaseBuilder(
        context.applicationContext,
        ContactDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val contactDao = database.contactDao()

    fun getContacts(): LiveData<List<Contact>> = contactDao.getContacts()
    fun getContact(id: UUID): LiveData<Contact?> = contactDao.getContact(id)
    fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)
    fun addContact(vararg contact: Contact) = contactDao.addContacts(*contact)

    companion object {
        private var INSTANCE:  ContactRepository? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = ContactRepository(context)
            }
        }

        fun get(): ContactRepository{
            return INSTANCE ?:
                    throw IllegalStateException("Contact Repository must be initialized")
        }
    }



}