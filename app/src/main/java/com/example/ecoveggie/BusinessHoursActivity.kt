package com.example.ecoveggie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.ecoveggie.Api.RetrofitClient
import com.example.ecoveggie.databinding.ActivityBusinessHoursBinding
import com.example.ecoveggie.databinding.ActivityMainBinding
import com.example.ecoveggie.databinding.ActivityRegisterBinding
import com.example.ecoveggie.view_model.SignupViewModel
import nl.joery.timerangepicker.TimeRangePicker

class BusinessHoursActivity : AppCompatActivity() {
    private lateinit var viewModel: SignupViewModel
    private lateinit var mBinding: ActivityBusinessHoursBinding
    private lateinit var mBinding2: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBusinessHoursBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        val backToVerification = findViewById<ImageView>(R.id.backBusinessHours)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        viewModel.signupResult.observe(this, Observer { result ->
            when (result) {
                SignupViewModel.SignupResult.Success -> {
                    // Show success message and navigate to main activity
                    Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                is SignupViewModel.SignupResult.Error -> {
                    // Show error message
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        btnSignUp.setOnClickListener {
             val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)

        }

        backToVerification.setOnClickListener {
            val intent = Intent(this, VerificationActivity::class.java)
            startActivity(intent)
        }

        mBinding.timePicker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {
            override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
                Log.d("TimeRangePicker", "Start time: " + startTime)
            }

            override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
                Log.d("TimeRangePicker", "End time: " + endTime.hour)
            }

            override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
                Log.d("TimeRangePicker", "Duration: " + duration.hour)
            }
        })



    }
}