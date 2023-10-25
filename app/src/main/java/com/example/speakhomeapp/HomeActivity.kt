package com.example.speakhomeapp

import Services.ProfileService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    lateinit var profileService:ProfileService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_view)

        // Obtener una referencia al TextView
        val textViewUserName: TextView = findViewById(R.id.textViewUserName)

        // Obtener la aplicación y acceder a los datos de la sesión
        val app = application as MyApplication
        val userProfile = app.profile

        // Asignar el nombre del usuario al TextView
        textViewUserName.text = userProfile?.userName

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        profileService = retrofit.create<ProfileService>(ProfileService::class.java)
    }
}