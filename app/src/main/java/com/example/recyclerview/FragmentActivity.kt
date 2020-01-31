package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val myFragment = FragmentForm()

        fragmentTransaction.add(R.id.fragment_id, myFragment)

    }
}
