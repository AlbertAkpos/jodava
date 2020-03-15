package com.example.recyclerview

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Util {

    private var userRef = FirebaseDatabase.getInstance().reference

    fun confirmRegistrationDetails(email: String,
                                   name: String, password: String,
                                   confrimPassword: String,
                                   phoneNumber: String, context: Context): Boolean{

        var result = true
        when {
            TextUtils.isEmpty(email) -> {

                Toast.makeText(context, "Email is empty",  Toast.LENGTH_SHORT).show()
                result = false
            }
            TextUtils.isEmpty(name) -> {
                Toast.makeText(context, "Name is empty",  Toast.LENGTH_SHORT).show()
                result = false
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(context, "Password is empty",  Toast.LENGTH_SHORT).show()
                result = false

            }
            TextUtils.isEmpty(confrimPassword) -> {
                Toast.makeText(context, "Confirm password is empty",  Toast.LENGTH_SHORT).show()
                result = false
            }
            else -> {
                        val mAuth = FirebaseAuth.getInstance()
                        mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    result = saveUser(name, email, phoneNumber, context)
                                } else{
                                    mAuth.signOut()
                                    result = false
                                    val message = task.exception.toString()
                                    Toast.makeText(context, "Registration not successful", Toast.LENGTH_LONG).show()

                                }
                            }


            }
        }
        return result
    }


    private fun saveUser(name: String,
                         email: String,
                         phoneNumber: String,
                         context: Context): Boolean{
        var result = false
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        userRef = userRef.child("contacts")
        var userMap = HashMap<String, Any>()

        userMap["uid"] = currentUserId!!
        userMap["name"] = name
        userMap["email"] = email
        userMap["phone_number"] = phoneNumber
        userMap["image"] = "gs://contact-app-e89af.appspot.com/gallery folder/profile.png"

        userRef.child(currentUserId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Registration successful",  Toast.LENGTH_SHORT).show()
                    result = true
                } else {
                    Toast.makeText(context, "Saving not successful",  Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()

                }
            }
        return result
    }

}