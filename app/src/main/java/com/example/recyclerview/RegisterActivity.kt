package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        login_link.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        register_button.setOnClickListener {
            val email = login_email.text.toString().trim()
            val name = register_name.text.toString().trim()
            val password = login_password.text.toString().trim()
            val confirmPassword = register_confirm_password.text.toString().trim()
            val phoneNumber = phone_number.text.toString().trim()

              Util.confirmRegistrationDetails(email, name, password, confirmPassword, phoneNumber,  this@RegisterActivity)





        }

    }



}
