package com.example.recyclerview.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.adapter.ContactAdapter
import com.example.recyclerview.databinding.MainFragmentBinding
import com.example.recyclerview.models.Contact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class MainFragment: Fragment() {
    var check by Delegates.notNull<Boolean>()
    lateinit var adapter: ContactAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var binding: MainFragmentBinding
    private lateinit var contacts: ArrayList<Contact>
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var layoutManager: LinearLayoutManager
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        contacts = arrayListOf()
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        layoutManager = LinearLayoutManager(context)
        adapter = ContactAdapter(contacts)
        recyclerView = binding.list
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO){
            getRemoteContacts(uid!!)
        }


        floating_btn.setOnClickListener {  view ->
            val action = MainFragmentDirections.actionMainFragmentToFormFragment2()
            findNavController().navigate(action)
        }

    }

     private  fun getRemoteContacts(uid: String) {

        FirebaseDatabase.getInstance().reference.child("contact-list").child(uid).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                contacts.clear()
                for (snapShot in dataSnapshot.children){
                    val contact = snapShot.getValue(Contact::class.java)
                    contacts.add(contact!!)
                }

                adapter = ContactAdapter(contacts)

                recyclerView.adapter = adapter
                binding.progressBar.visibility = View.INVISIBLE
                adapter.notifyDataSetChanged()

            }
        })
    }
}