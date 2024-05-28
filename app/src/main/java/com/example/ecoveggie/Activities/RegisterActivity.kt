package com.example.ecoveggie.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecoveggie.FarmInfoActivity
import com.example.ecoveggie.HomeActivity
import com.example.ecoveggie.LoginActivity
import com.example.ecoveggie.MainActivity
import com.example.ecoveggie.R
import com.example.ecoveggie.databinding.ActivityRegisterBinding
import com.example.ecoveggie.view_model.SignupViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.nameText.onFocusChangeListener = this
        mBinding.emailText.onFocusChangeListener = this
        mBinding.phnNum.onFocusChangeListener = this
        mBinding.passWord.onFocusChangeListener = this
        mBinding.rePass.onFocusChangeListener = this

        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        val fullName = findViewById<EditText>(R.id.nameText)
        val emailText = findViewById<EditText>(R.id.emailText)
        val phnNum = findViewById<EditText>(R.id.phnNum)
        val passWord = findViewById<EditText>(R.id.passWord)
        val rePass = findViewById<EditText>(R.id.rePass)
        val googleReg = findViewById<ImageButton>(R.id.imgGoogleReg)
        val fbReg = findViewById<ImageButton>(R.id.imgFbReg)

        googleReg.setOnClickListener {
            regSignIn()
        }

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, FarmInfoActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        fullName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.fullname.value = s.toString()
            }
        })

        emailText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.email.value = s.toString()
            }
        })
        phnNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.phone.value = s.toString()
            }
        })
        passWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.password.value = s.toString()
            }
        })

        val btnContinue = findViewById<Button>(R.id.cntnueRegister)
        btnContinue.setOnClickListener {

            val fullName = mBinding.nameText.text.toString().trim()
            val email = mBinding.emailText.text.toString().trim()
            val phnNum = mBinding.phnNum.text.toString().trim()
            val password = mBinding.passWord.text.toString().trim()
            val rePassword = mBinding.rePass.text.toString().trim()

            if(fullName.isEmpty()){
                mBinding.nameText.requestFocus()
                return@setOnClickListener
            }
            if(email.isEmpty()){
                mBinding.emailText.requestFocus()
                return@setOnClickListener
            }
            if(phnNum.isEmpty()){
                mBinding.phnNum.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                mBinding.passWord.requestFocus()
                return@setOnClickListener
            }
            if(rePassword.isEmpty()){
                mBinding.rePass.requestFocus()
                return@setOnClickListener
            }else{
                val intent = Intent(this,FarmInfoActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun validateFullName(): Boolean{
        var errorMessage: String? = null
        val value: String = mBinding.nameText.text.toString()
        if(value.isEmpty()){
            errorMessage = "Full name is required"
        }

        if(errorMessage != null){
            mBinding.nameTextLay.apply {
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateEmail(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.emailText.text.toString()
        if(value.isEmpty()){
            errorMessage = "Email is required"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage = "Email address is invalid"
        }

        if(errorMessage != null){
            mBinding.emailTextLay.apply {
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePhoneNum(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.phnNum.text.toString()
        if(value.isEmpty()){
            errorMessage = "Phone number is required"
        }else if(!Patterns.PHONE.matcher(value).matches()){
            errorMessage = "Phone number is invalid"
        }

        if(errorMessage != null){
            mBinding.phnNumLay.apply {
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePassword(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.passWord.text.toString()
        if(value.isEmpty()){
            errorMessage = "Password is required"
        }else if(value.length < 6){
            errorMessage = "Password should contain minimum 6 characters"
        }

        if(errorMessage != null){
            mBinding.passWordLay.apply {
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateConfirmPassword(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.rePass.text.toString()
        if(value.isEmpty()){
            errorMessage = "Confirm your password"
        }else if(value.length < 6){
            errorMessage = "Confirm your password with minimum 6 characters"
        }

        if(errorMessage != null){
            mBinding.rePassLay.apply {
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePasswordAndConfirmPassword(): Boolean{
        var errorMessage: String? = null
        val password = mBinding.passWord.text.toString()
        val rePassword = mBinding.rePass.text.toString()
        if(password != rePassword){
            errorMessage = "Please enter the same password for confirmation!"
        }
        return errorMessage == null
    }

    override fun onClick(view: View?) {
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(view != null){
            when(view.id){
                R.id.nameText -> {
                    if(hasFocus){
                        if(mBinding.nameTextLay.isErrorEnabled){
                            mBinding.nameTextLay.isErrorEnabled= false
                        }
                    }else{
                        validateFullName()
                    }
                }
                R.id.emailText -> {
                    if(hasFocus){
                        if(mBinding.emailTextLay.isErrorEnabled){
                            mBinding.emailTextLay.isErrorEnabled= false
                        }
                    }else{
                        if(validateEmail()){

                        }
                    }
                }
                R.id.phnNum -> {
                    if(hasFocus){
                        if(mBinding.phnNumLay.isErrorEnabled){
                            mBinding.phnNumLay.isErrorEnabled= false
                        }

                    }else{
                        validatePhoneNum()
                    }

                }
                R.id.passWord -> {
                    if(hasFocus){
                        if(mBinding.passWordLay.isErrorEnabled){
                            mBinding.passWordLay.isErrorEnabled= false
                        }

                    }else{
                        if(validatePassword() && mBinding.rePass.text!!.isEmpty() && validateConfirmPassword() && validatePasswordAndConfirmPassword()){
                            if(mBinding.rePassLay.isErrorEnabled){
                                mBinding.rePassLay.isErrorEnabled = false
                            }
                            mBinding.rePassLay.setStartIconDrawable(R.drawable.check_circle)
                        }
                    }

                }
                R.id.rePass -> {
                    if(hasFocus){
                        if(mBinding.rePassLay.isErrorEnabled){
                            mBinding.rePassLay.isErrorEnabled= false
                        }

                    }else{
                        if(validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()){
                            if(mBinding.passWordLay.isErrorEnabled){
                                mBinding.passWordLay.isErrorEnabled = false
                            }
                            mBinding.rePassLay.setStartIconDrawable(R.drawable.check_circle)
                        }
                    }

                }
            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    private fun regSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, LoginActivity.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LoginActivity.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Continuing as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, FarmInfoActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}