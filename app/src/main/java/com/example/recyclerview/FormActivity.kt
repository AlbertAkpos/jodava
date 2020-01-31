package com.example.recyclerview

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)


        add_contact_btn.setOnClickListener {
            val name: String = name_input.text.toString()
            val email: String = email_input.text.toString()
            val phone_number: String = phone_number_input.text.toString()

            if (name.isEmpty() || phone_number.isEmpty()) {
                Toast.makeText(this, "Name or phone number cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (phone_number.length < 11 || phone_number[0] != '0') {
                Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Save contact?").setCancelable(false)
                    .setPositiveButton("Proceed"
                    ) { dialog, id ->
                        Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT)
                            .show()


                        //Clear input fields
                        name_input.text.clear()
                        phone_number_input.text.clear()
                        email_input.text.clear()

                        saveContact(name, email, phone_number)

                    }
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("Save contact")
                alert.show()
            }

        }


    }


    fun saveContact(name: String, email: String, phone_number:String) {
        val db = AppDatabase.getDatabase(applicationContext)
        db.contactDao().insertAll(Contact(name=name, email = email, phone_number = phone_number, uid = 0))

        val intent = Intent()
        setResult(GALLERY, intent)
        finish()

    }
}

