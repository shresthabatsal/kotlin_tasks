package com.example.tasks

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasks.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    var countries = arrayOf(
        "Nepal", "China", "Bhutan", "Pakistan", "Canada", "Australia", "India")

    var cities = arrayOf("Kathmandu", "Bhaktapur", "Lalitpur", "Kirtipur", "Kanchanpur")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.spinnerCountry
        val adapter = ArrayAdapter(
            this@SignupActivity,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )
        spinner.adapter = adapter
        spinner.setSelection(0)

        val autoComplete: AutoCompleteTextView = binding.cityChoose
        val autoAdapter = ArrayAdapter(
            this@SignupActivity,
            android.R.layout.simple_list_item_1,
            cities
        )
        autoComplete.setAdapter(autoAdapter)
        autoComplete.threshold = 1

        binding.signupButton.setOnClickListener {
            val username: String = binding.editName.text.toString()
            val email: String = binding.editEmailaddress.text.toString()
            val password: String = binding.editPassword.text.toString()
            val selectedGenderId: Int = binding.genderSelect.checkedRadioButtonId
            val isTermsChecked: Boolean = binding.agreeCheckbox.isChecked
            val selectedCountry: String = binding.spinnerCountry.selectedItem?.toString() ?: ""
            val selectedCity: String = binding.cityChoose.text.toString()

            val selectedGender: String = when (selectedGenderId) {
                binding.radioButtonMale.id -> "Male"
                binding.radioButtonFemale.id -> "Female"
                else -> ""
            }

            if(username.isEmpty()){
                binding.editName.error = "Please fill this field."
            }
            else if(email.isEmpty()){
                binding.editEmailaddress.error = "Please fill this field."
            }
            else if(password.isEmpty()){
                binding.editPassword.error= "Please fill this field."
            }
            else if (selectedGenderId == -1) {
                Toast.makeText(this, "Please select a gender.", Toast.LENGTH_SHORT).show()
            }
            else if (selectedCity.isEmpty()) {
                binding.cityChoose.error = "Please enter a city."
            }else if (!isTermsChecked) {
                Toast.makeText(this, "Please agree to the terms and conditions.", Toast.LENGTH_SHORT).show()
            }else{
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("email", email)
            intent.putExtra("gender", selectedGender)
            intent.putExtra("country", selectedCountry)
            intent.putExtra("city", selectedCity)
            startActivity(intent)
            finish()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editEmail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}