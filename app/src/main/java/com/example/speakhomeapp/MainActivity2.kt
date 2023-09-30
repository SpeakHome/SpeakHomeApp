package com.example.speakhomeapp

import Model.User
import Services.UserService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val editTextUserName = findViewById<EditText>(R.id.editTextUserName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val switchITechnician = findViewById<Switch>(R.id.switchITechnician)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://your-api-url.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UserService::class.java)

        val user = User(
            userName = editTextUserName.text.toString(),
            email = editTextEmail.text.toString(),
            password = editTextPassword.text.toString(),
            roleId = if (switchITechnician.isChecked) 1 else 0
        )

        buttonSignUp.setOnClickListener {
            service.create("users", user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        // Handle successful response
                    } else {
                        // Handle error response
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    // Handle failure
                }
            })
        }
    }
}