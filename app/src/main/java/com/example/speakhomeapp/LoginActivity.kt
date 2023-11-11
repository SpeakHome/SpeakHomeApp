package com.example.speakhomeapp

import Models.ApiResponse
import Models.Profile.ProfileResource
import Models.Requests.LoginRequest
import Services.ProfileService
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    lateinit var profileService:ProfileService

    // Crea una referencia a SharedPreferences
    private val preferences by lazy { getSharedPreferences("loginPreferences", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        val app = applicationContext as MyApplication // Añade esta línea para acceder a MyApplication

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val checkBoxRemember = findViewById<CheckBox>(R.id.checkBoxRemember)
        val registerTextView = findViewById<TextView>(R.id.txtNoAccount)
        val buttonSignIn = findViewById<Button>(R.id.buttonSignIn)

        // Rellena las credenciales almacenadas si están disponibles
        editTextEmail.setText(preferences.getString("email", ""))
        editTextPassword.setText(preferences.getString("password", ""))
        checkBoxRemember.isChecked = preferences.getBoolean("remember", false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.47:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        profileService = retrofit.create<ProfileService>(ProfileService::class.java)

        // Listener para la opción de registro
        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Listener para el botón de inicio de sesión
        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val loginRequest = LoginRequest(email, password)

            profileService.login(loginRequest).enqueue(object : Callback<ProfileResource> {
                override fun onResponse(call: Call<ProfileResource>, response: Response<ProfileResource>) {
                    if (response.isSuccessful) {
                        val userProfile = response.body()

                        userProfile?.let {
                            // Login exitoso
                            Toast.makeText(this@LoginActivity, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()

                            // Guardar las credenciales si el checkBox está marcado
                            with(preferences.edit()) {
                                if (checkBoxRemember.isChecked) {
                                    putString("email", email)
                                    putString("password", password)
                                    putBoolean("remember", true)
                                } else {
                                    remove("email")
                                    remove("password")
                                    putBoolean("remember", false)
                                }
                                apply()
                            }

                            // Guarda la información del perfil y establece isAuthenticated en true
                            app.profile = it
                            app.isAuthenticated = true

                            // Navegar a HomeActivity
                            val homeIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(homeIntent)
                            finish()
                        } ?: run {
                            Toast.makeText(this@LoginActivity, "Perfil no encontrado", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@LoginActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProfileResource>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Fallo: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}