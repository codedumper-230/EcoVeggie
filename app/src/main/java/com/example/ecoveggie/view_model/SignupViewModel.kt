package com.example.ecoveggie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecoveggie.data.User

class SignupViewModel: ViewModel() {

    val fullname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val role = MutableLiveData<String>()
    val businessName = MutableLiveData<String>()
    val informalName = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val state = MutableLiveData<String>()
    val zipcode = MutableLiveData<Number>()
    val registrationProof = MutableLiveData<String>()
    val businessHours = MutableLiveData<String>()
    val deviceToken = MutableLiveData<String>()
    val type = MutableLiveData<String>()
    val socialId = MutableLiveData<String>()
    // ... other fields

    private val _signupResult = MutableLiveData<SignupResult>()
    val signupResult: LiveData<SignupResult> = _signupResult

    // Function to call Retrofit for signup
    fun signup() {
        val user = User(
            fullname.value!!,
            email.value!!,
            phone.value!!,
            password.value!!,
            role.value!!,
            businessName.value!!,
            informalName.value!!,
            address.value!!,
            city.value!!,
            state.value!!,
            zipcode.value!!,
            registrationProof.value!!,
            businessHours.value!!,
            deviceToken.value!!,
            type.value!!,
            socialId.value!!
        )

        // Replace with your actual Retrofit call and error handling
        // ... using Retrofit with user data ...

        _signupResult.postValue(SignupResult.Success)
        _signupResult.postValue(SignupResult.Error("Signup failed"))

    }

    // Sealed class to represent signup result (Success or Error)
    sealed class SignupResult {
        object Success : SignupResult()
        data class Error(val message: String) : SignupResult()
    }
}