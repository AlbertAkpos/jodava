package com.example.recyclerview.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityFormBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_form.*
import kotlin.properties.Delegates


class FormFragment : Fragment() {
    lateinit var binding: ActivityFormBinding
    private val firbaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.activity_form, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_contact_btn.setOnClickListener {
            addContactCallback()
        }
    }

    private fun addContactCallback(){
        val name: String = name_input.text.toString()
        val email: String = email_input.text.toString()
        val phone_number: String = phone_number_input.text.toString()

        if (name.isEmpty() || phone_number.isEmpty()) {
            Toast.makeText(context, "Name or phone number cannot be empty", Toast.LENGTH_SHORT)
                .show()
        } else if (phone_number.length < 11 || phone_number[0] != '0') {
            Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show()
        } else {
            val dialogBuilder = AlertDialog.Builder(context!!)
            dialogBuilder.setMessage("Save contact?").setCancelable(false)
                .setPositiveButton("Proceed"
                ) { dialog, id ->
                    Toast.makeText(context!!, "Contact saved successfully", Toast.LENGTH_SHORT)
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


    private fun saveContact(name: String, email: String, phone_number:String) {
        val defaultImage = "gs://contact-app-e89af.appspot.com/gallery folder/profile.png"


        //build new contact
        val newContact = HashMap<String, Any>()
        newContact["name"] = name
        newContact["email"] = email
        newContact["phone_number"] = phone_number
        newContact["image"] = defaultImage

        firbaseUser?.uid.let { uid ->
            FirebaseDatabase.getInstance().reference
                .child("contact-list").child(uid.toString())
                .child(name)
                .setValue(newContact)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) findNavController().popBackStack() else Toast.makeText(context, "Error saving contact", Toast.LENGTH_LONG).show()
                }

        }

    }
}

