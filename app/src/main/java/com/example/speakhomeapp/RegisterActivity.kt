package com.example.speakhomeapp

import Models.Profile.CreateProfile
import Models.Profile.ProfileResource
import Services.ProfileService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    lateinit var userService:ProfileService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_view)

        val editTextUserName = findViewById<EditText>(R.id.editTextUserName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val switchTechnician = findViewById<Switch>(R.id.switchTechnician)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userService = retrofit.create<ProfileService>(ProfileService::class.java)

        buttonSignUp.setOnClickListener {

            val user = CreateProfile(
                userName = editTextUserName.text.toString(),
                email = editTextEmail.text.toString(),
                password = editTextPassword.text.toString(),
                roleId = if (switchTechnician.isChecked) 1 else 2
            )

            userService.create(user).enqueue(object : Callback<ProfileResource> {
                override fun onResponse(call: Call<ProfileResource>, response: Response<ProfileResource>) {
                    if (response.isSuccessful) {
                        // Handle successful response
                        Toast.makeText(this@RegisterActivity, "User Created Successfully!", Toast.LENGTH_SHORT).show()
                        // Navigate to another activity
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Handle error response
                        Toast.makeText(this@RegisterActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProfileResource>, t: Throwable) {
                    // Handle failure
                    Toast.makeText(this@RegisterActivity, "Failure: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}