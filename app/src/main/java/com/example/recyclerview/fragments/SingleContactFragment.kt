package com.example.recyclerview.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.recyclerview.MainActivity
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivitySingleContactBinding
import com.example.recyclerview.models.Contact
import kotlinx.android.synthetic.main.activity_main.*

private const val CALL_PERMISSION = 0
private const val GALLERY_PERMISSION = 1

class SingleContactFragment : Fragment() {

    lateinit var contact: Contact

    private lateinit var binding: ActivitySingleContactBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.activity_single_contact, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contact = arguments?.getParcelable<Contact>("contact")!!
        binding.contact = contact
        (requireActivity() as MainActivity).my_toolbar.title = contact.name

        binding.phone.setOnClickListener{
            //Check if permission has been given to make a call
            if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //If no permission, request permission
                this.requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), CALL_PERMISSION)
            }
            else
            {
                makeCall()
            }
        }


        binding.emailEmail.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type ="message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contact?.email))

            try {
                startActivity(Intent.createChooser(emailIntent, "Choose Email Client"))
            } catch (e: Exception){
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }


        binding.profilePic.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            if (ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //If no permission, request permission
                this.requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_PERMISSION)
            } else {
                startActivityForResult(galleryIntent, GALLERY_PERMISSION)
            }
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            CALL_PERMISSION -> {
                if (grantResults.isNotEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED) makeCall()
            }
        }
    }




    private fun makeCall(){
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:" + contact?.phone_number)
        startActivity(callIntent)
    }

}
