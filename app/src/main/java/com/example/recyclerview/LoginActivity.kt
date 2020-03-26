package com.example.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register_link.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }


        login_button.setOnClickListener { view ->

            val email = login_email.text.toString()
            val password = login_password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
               showError(view, "Fill in all fields")
                if (email.isEmpty()) login_email_layout.error =
                    "Email cannot be empty" else password_layout.error = "Password cannot be empty"

            } else {
                progress_bar.visibility = View.VISIBLE
                logUserIn(email, password, view, progress_bar)
            }

        }

        forgot_password_link.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

    }

    private fun logUserIn(email: String, password: String, view: View, progressbar: ProgressBar) {
        val mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    progressbar.visibility = View.INVISIBLE
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    progressbar.visibility = View.INVISIBLE
                    when (task.exception){
                        is FirebaseAuthInvalidUserException -> {
                            showError(view, "Invalid credentials")
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            showError(view, "Email and password do not match")
                        }
                        else -> {
                            showError(view, "An error occurred. Try again after some time")
                        }
                    }

                }
            }



    }

    private fun showError(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.error))
        snackbar.setTextColor(ContextCompat.getColor(this, R.color.background))
        snackbar.show()
    }


    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }


}
