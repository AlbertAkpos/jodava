package com.example.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.zip.Inflater


class FragmentForm: Fragment() {
    override fun onCreateView( inflater: LayoutInflater, viewGroup: ViewGroup?, saveIntance: Bundle?): View{
        val view = inflater.inflate(R.layout.fragment_form, viewGroup, false)

        val emailInput = view.findViewById<TextView>(R.id.email)
        return view
    }
}