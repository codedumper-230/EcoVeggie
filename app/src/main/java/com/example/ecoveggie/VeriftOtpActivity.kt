package com.example.ecoveggie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class VeriftOtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verift_otp)

        val otpBox1 = findViewById<EditText>(R.id.otpBox1)
        val otpBox2 = findViewById<EditText>(R.id.otpBox2)
        val otpBox3 = findViewById<EditText>(R.id.otpBox3)
        val otpBox4 = findViewById<EditText>(R.id.otpBox4)
        val otpBox5 = findViewById<EditText>(R.id.otpBox5)
        val otpBox6 = findViewById<EditText>(R.id.otpBox6)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val intent = Intent(this,ResetActivity::class.java)
            startActivity(intent)
        }

        val textLogin2 = findViewById<TextView>(R.id.txtLogin3)
        textLogin2.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }
}