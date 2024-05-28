package com.example.ecoveggie

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ecoveggie.view_model.SignupViewModel

class VerificationActivity : AppCompatActivity() {

    private lateinit var viewModel: SignupViewModel
    lateinit var camera: ImageButton
    lateinit var imgDoc: ImageView
    val REQUEST_IMAGE_CAPTURE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)


        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)


        val backToFarmInfo = findViewById<ImageView>(R.id.backVerification)
        val btnSubmit = findViewById<Button>(R.id.btnVerifiSubmit)
        camera = findViewById(R.id.btnCamera)
        imgDoc = findViewById(R.id.imgDoc)

        backToFarmInfo.setOnClickListener {
            val intent = Intent(this,FarmInfoActivity::class.java)
            startActivity(intent)
        }

        btnSubmit.setOnClickListener {
            val intent = Intent(this,BusinessHoursActivity::class.java)
            startActivity(intent)
        }

        camera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try{
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this,"Error: "+ e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imgDoc.setImageBitmap(imageBitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }



}