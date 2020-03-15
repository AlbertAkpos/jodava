package com.example.recyclerview.fragments

import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceFragmentCompat
import com.example.recyclerview.MainActivity
import com.example.recyclerview.R


class SettingsFragment: PreferenceFragmentCompat() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_layout, rootKey)

        val listPreference = preferenceManager.findPreference("night_mood_preference")
        listPreference.setOnPreferenceChangeListener { preference, newValue ->

           val sharedPreferences = activity?.getSharedPreferences("night_mode", Context.MODE_PRIVATE)
           val sharedPreferenceEditor = sharedPreferences?.edit()

           if(newValue == true){
               sharedPreferenceEditor?.putBoolean("mode", true)
               sharedPreferenceEditor?.apply()

               TaskStackBuilder.create(activity)
                   .addNextIntent(Intent(activity, MainActivity::class.java))
                   .addNextIntent(activity!!.intent)
                   .startActivities()
           } else {
               sharedPreferenceEditor?.putBoolean("mode", false)
               sharedPreferenceEditor?.apply()

               TaskStackBuilder.create(activity)
                   .addNextIntent(Intent(activity, MainActivity::class.java))
                   .addNextIntent(activity!!.intent)
                   .startActivities()
           }
            true
        }
    }




}