package com.example.ecoveggie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ForgotPassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        val sendCode = findViewById<Button>(R.id.btnSendCode)
        val textLogin = findViewById<TextView>(R.id.txtLogin)

        sendCode.setOnClickListener {
            val intent = Intent(this, VeriftOtpActivity::class.java)
            startActivity(intent)
        }

        textLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}