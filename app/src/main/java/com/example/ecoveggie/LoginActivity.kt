package com.example.ecoveggie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.ecoveggie.Activities.RegisterActivity
import com.example.ecoveggie.databinding.ActivityLoginBinding
import com.example.ecoveggie.databinding.ActivityRegisterBinding
import com.example.ecoveggie.view_model.SignupViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity(), View.OnFocusChangeListener {

    companion object {
        const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var viewModel: SignupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()



        val currentUser = auth.currentUser
        val loginEmail = findViewById<EditText>(R.id.emailLogin)
        val loginPass = findViewById<EditText>(R.id.passLogin)
        val forgotPass = findViewById<TextView>(R.id.forgotPassTxt)
        val createAcc = findViewById<TextView>(R.id.createAcc)
        val btnLogin = findViewById<Button>(R.id.btnLogin2)
        val googleSignIn = findViewById<ImageButton>(R.id.imgGoogle)
        val fbSignIn = findViewById<ImageButton>(R.id.imgFb)

        googleSignIn.setOnClickListener {
            signIn()
        }

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }

        btnLogin.setOnClickListener {
            Toast.makeText(this,"Please create an account to login", Toast.LENGTH_SHORT).show()
        }

        mBinding.emailLogin.onFocusChangeListener = this
        mBinding.passLogin.onFocusChangeListener = this

        createAcc.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        forgotPass.setOnClickListener{
            val intent = Intent(this,ForgotPassActivity:: class.java)
            startActivity(intent)
        }
    }

    private fun validateLoginEmail(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.emailLogin.text.toString()
        if(value.isEmpty()){
            errorMessage = "Email is required"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage = "Email address is invalid"
        }

        if(errorMessage != null){
            mBinding.emailLoginLay.apply {
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateLoginPassword(): Boolean{
        var errorMessage: String? = null
        val value = mBinding.passLogin.text.toString()
        if(value.isEmpty()){
            errorMessage = "Password is required"
        }else if(value.length < 6){
            errorMessage = "Password should contain minimum 6 characters"
        }

        if(errorMessage != null){
            mBinding.passLoginLay.apply {
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.emailLogin -> {
                    if (hasFocus) {
                        if (mBinding.emailLoginLay.isErrorEnabled) {
                            mBinding.emailLoginLay.isErrorEnabled = false
                        }
                    } else {
                        validateLoginEmail()
                    }
                }

                R.id.passLogin -> {
                    if (hasFocus) {
                        if (mBinding.passLoginLay.isErrorEnabled) {
                            mBinding.passLoginLay.isErrorEnabled = false
                        }
                    } else {
                        if (validateLoginPassword()) {

                        }
                    }
                }
            }
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
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
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}