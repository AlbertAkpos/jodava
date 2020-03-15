package com.example.recyclerview

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class ContactApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        ContactRepository.initialize(this)
    }
}