package com.example.recyclerview

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

val GALLERY_PERMISSION = 1
val GALLERY = 2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.getDatabase(applicationContext).contactDao()

        val allContacts = db.getAllContacts() as ArrayList<Contact>
        if(allContacts.isNotEmpty()) setRecyclerView(allContacts, this)


        floating_btn.setOnClickListener {


            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_PERMISSION
                )

            } else {
                val formActivityIntent = Intent(this, FormActivity::class.java)
                startActivityForResult(formActivityIntent, GALLERY)
            }

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            val contacts = AppDatabase.getDatabase(applicationContext).contactDao().getAllContacts() as ArrayList<Contact>
            setRecyclerView(contacts, this)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == GALLERY_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val formActivityIntent = Intent(this, FormActivity::class.java)
                startActivityForResult(formActivityIntent, GALLERY)
            }
        }
    }


    fun setRecyclerView(allContacts: ArrayList<Contact>, context: Context) {
        val layoutManager = LinearLayoutManager(context)
        list.layoutManager = layoutManager
        list.adapter = ContactsAdapter(allContacts, context)
    }


}
