package com.example.ecoveggie

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.ecoveggie.Activities.RegisterActivity
import com.example.ecoveggie.view_model.SignupViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class FarmInfoActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: SignupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_info)

        mAuth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        val btnBackToRegister = findViewById<ImageView>(R.id.backFarmInfo)
        val btnContinue2 = findViewById<Button>(R.id.btnFarmCntnue)
        val stateSpinner = findViewById<Spinner>(R.id.stateSpinner)
        val states = resources.getStringArray(R.array.indian_states)
        val businessText = findViewById<EditText>(R.id.businessText)
        val informalText = findViewById<EditText>(R.id.informalText)
        val address = findViewById<EditText>(R.id.address)
        val city = findViewById<EditText>(R.id.city)
        val zipcode = findViewById<EditText>(R.id.zipCode)

        businessText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.businessName.value = s.toString()
            }
        })
        informalText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.informalName.value = s.toString()
            }
        })
        address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.address.value = s.toString()
            }
        })
        city.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.city.value = s.toString()
            }
        })
        stateSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCity = parent.getItemAtPosition(position).toString() // Get the selected city
                viewModel.state.value = selectedCity  // Update ViewModel with the selected city
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: Handle the case when no item is selected
            }
        })
        zipcode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.zipcode.value = s.toString().toIntOrNull()
            }
        })

        btnBackToRegister.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        btnContinue2.setOnClickListener {

            if(viewModel.businessName.value?.isEmpty() == true){
                businessText.requestFocus()
                return@setOnClickListener
            }
            if(viewModel.informalName.value?.isEmpty() == true){
                informalText.requestFocus()
                return@setOnClickListener
            }
            if(viewModel.address.value?.isEmpty() == true){
                address.requestFocus()
                return@setOnClickListener
            }
            if(viewModel.city.value?.isEmpty() == true){
                city.requestFocus()
                return@setOnClickListener
            }
            if(viewModel.state.value?.isEmpty() == true){
                stateSpinner.requestFocus()
                return@setOnClickListener
            }
            if(viewModel.zipcode.value == null) {
                zipcode.requestFocus()
                return@setOnClickListener
            }else {
                val intent = Intent(this, VerificationActivity::class.java)
                startActivity(intent)
            }
        }

        val spinnerAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states){
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                if(position == 0){
                    view.setTextColor(Color.GRAY)
                }else{
                    view.setTextColor(Color.BLACK)
                }
                return view
            }
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        stateSpinner.adapter = spinnerAdapter

        stateSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if(value == states[0]){
                    (view as TextView).setTextColor(Color.GRAY)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
}