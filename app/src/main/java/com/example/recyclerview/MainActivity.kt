package com.example.recyclerview

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.recyclerview.fragments.FormFragment
import kotlinx.android.synthetic.main.activity_main.*

const val GALLERY_PERMISSION = 1
const val GALLERY = 2
const val NAME_OF_DATABASE = "contact_database"

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(this.my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(my_toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == GALLERY_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val formActivityIntent = Intent(this, FormFragment::class.java)
                startActivityForResult(formActivityIntent, GALLERY)
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||  super.onSupportNavigateUp()
    }





}
