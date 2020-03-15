package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //setting up toolbar
        setSupportActionBar(reg_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
       reg_toolbar.setNavigationOnClickListener {
           finish()
       }

        register_button.setOnClickListener {
            val email = login_email.text.toString().trim()
            val name = register_name.text.toString().trim()
            val password = login_password.text.toString().trim()
            val confirmPassword = register_confirm_password.text.toString().trim()
            val phoneNumber = phone_number.text.toString().trim()



            val result = Util.confirmRegistrationDetails(email, name, password, confirmPassword, phoneNumber,  this)
            if (result) {
                val intent = Intent(this, MainActivity::class.java)
                //SO THE USER CANNOT GO BACK TO SIGN UP PAGE IF HE DOESN'T LOG OUT
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            } else  {
                Snackbar.make(it, "Registration error. Please check credentials", Snackbar.LENGTH_LONG ).show()
            }


        }

    }



}
