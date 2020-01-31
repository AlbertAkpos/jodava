package com.example.recyclerview

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ContactsAdapter(val list: List<Contact>, val context: Context): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        //parent represent the ContactViewHolder

        //The inflater makes it possible it treat the view as a class or an object
        val inflater = LayoutInflater.from(parent.context)

        //The view below represent the contact_view layout
        val view = inflater.inflate(R.layout.contact_view, parent, false)
        return ContactViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        //Get the contact/person in the current position
        val contact = list[position]
        holder.name.text = contact.name
        holder.email.text = contact.email
        holder.phone_number.text = contact.phone_number


        val noOfContacts = itemCount

        for(i in 0..noOfContacts){
        Glide.with(context).asBitmap()
            .load("https://source.unsplash.com/random")
            .error(R.drawable.ic_person_profile)
            .into(holder.image_id)
        }


        //Setting a clickListener on the contact_view
        holder.itemView.setOnClickListener{
            val intent = Intent(context, SingleContactActivity::class.java)
            val bundle = Bundle()

            //Parcelable extra
            intent.putExtra("parcelable", contact)

            startActivity(context, intent, bundle)

        }

        holder.itemView.setOnLongClickListener {
           val dialogueBulder = AlertDialog.Builder(context)
            dialogueBulder.setMessage("Delete ${contact.name}").setCancelable(false)
                .setPositiveButton("Delete"){ dialog, which ->
                    AppDatabase.getDatabase(context).contactDao().delete(contact)
                }
            true
        }

    }

    class ContactViewHolder(view: View): RecyclerView.ViewHolder(view){
        var name = view.findViewById<TextView>(R.id.name)
        var email = view.findViewById<TextView>(R.id.email)
        var phone_number = view.findViewById<TextView>(R.id.phone_number)
        var image_id = view.findViewById<ImageView>(R.id.image_id)
    }
}
