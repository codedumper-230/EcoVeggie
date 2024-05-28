package com.example.ecoveggie.data

import androidx.lifecycle.MutableLiveData

data class User(
    val fullname: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String,
    val businessName:String,
    val informalName:String,
    val address:String,
    val city:String,
    val state:String,
    val zipcode :Number,
    val registrationProof: String,
    val businessHours:String,
    val deviceToken: String,
    val type :String,
    val socialId :String
)
