package com.example.recyclerview

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setSupportActionBar(reset_pw_toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pw_reset_btn.setOnClickListener {
                reset_progressbar.visibility = View.VISIBLE
                val email = reset_email_input.text.toString()
                resetEmail(email, it)
        }


    }

    private fun resetEmail(email: String, view: View) {
        if(TextUtils.isEmpty(email)) {
            displayMessage("Email cant be empty", view)
            reset_progressbar.visibility = View.INVISIBLE
            return
        }

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                displayMessage("Reset password email sent", view)
            } else {
                displayMessage("Invalid email", view)
            }
            reset_progressbar.visibility = View.INVISIBLE
        }
    }

    private fun displayMessage(message: String, view: View) {
        val snackbar = Snackbar.make( view, message, Snackbar.LENGTH_LONG)
        snackbar.setTextColor(ContextCompat.getColor(this, R.color.background))
        snackbar.show()
    }
}