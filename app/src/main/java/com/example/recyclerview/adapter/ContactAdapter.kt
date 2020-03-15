package com.example.recyclerview.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ContactViewBinding
import com.example.recyclerview.models.Contact

class ContactAdapter(private val contacts: List<Contact>): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    lateinit var binding: ContactViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.contact_view, parent, false)
        return ContactViewHolder(
            binding
        )
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        var contact = contacts[position]
        val bundle = Bundle()
        bundle.putParcelable("contact", contact)

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.singleContactFragment, bundle)
        }

        holder.bind(contact)
    }

    class ContactViewHolder(var binding: ContactViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Contact){
            binding.contact = data
        }

    }
}