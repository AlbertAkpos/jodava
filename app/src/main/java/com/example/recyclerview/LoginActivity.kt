package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
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
                Snackbar.make(view, "Fill in all fields", Snackbar.LENGTH_SHORT).show()
                if (email.isEmpty()) login_email_layout.error = "Email cannot be empty" else  password_layout.error = "Password cannot be empty"

            } else {
                progress_bar.visibility = View.VISIBLE
                logUserIn(email, password, view, progress_bar)
            }

        }
    }

    private fun logUserIn(email: String, password: String, view: View, progressbar: ProgressBar) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressbar.visibility = View.INVISIBLE
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    val message = task.result.toString()
                    Snackbar.make(view, "$message", Snackbar.LENGTH_SHORT).show()
                }
            }
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
