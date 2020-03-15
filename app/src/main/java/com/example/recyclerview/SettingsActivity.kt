package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(this.settings_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.settings_layout,
                SettingsFragment()
            )
            .commit()



    }
}
