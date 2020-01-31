package com.example.recyclerview

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_single_contact.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class SingleContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_contact)



        //testing parcelable
        val contact = intent.getParcelableExtra<Contact>("parcelable")

        contact_name.text = contact.name
        contact_email.text = contact.email
        contact_phone.text = contact.phone_number


        //Set onClick Listener on the call icon
        phone.setOnClickListener{
            val callIntent = Intent(Intent.ACTION_CALL)

            callIntent.data = Uri.parse("tel:"+ contact.phone_number)
            //Check if permission has been given to make a call
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //If no permission, request permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.CALL_PHONE), 0)
            }
            else
            {
                startActivity(callIntent)
            }
        }

        email_email.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type ="message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contact.email))

            try {
                startActivity(Intent.createChooser(emailIntent, "Choose Email Client"))
            } catch (e: Exception){
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }


        //Add profile image
        profile_pic.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //If no permission, request permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                startActivityForResult(galleryIntent, 1)
            }
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("onActvityResult", "onActvityResult started")

        if( requestCode == 1){
            println("Request code match")
            if(data != null) {
                val contentURI = data!!.data

                try {
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                         val source =ImageDecoder.createSource(this.contentResolver, contentURI!!)

                         val bitmap = ImageDecoder.decodeBitmap(source)
                         profile_pic.setImageURI(contentURI)


                     }


                }catch (e: IOException){
                    e.printStackTrace()
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun onResume() {
        super.onResume()
        loadImage()

        println("""
                
                Hello from onResume
                
            """)
    }


    fun loadImage() {

        try {

            val file = File("/data/user/0/com.example.recyclerview/app_image1", "image1"+".png")
            val bmp = BitmapFactory.decodeStream(FileInputStream(file))

            if(bmp != null) profile_pic.setImageBitmap(bmp)

        } catch (e: Exception){
            e.printStackTrace()
        }
    }


}
