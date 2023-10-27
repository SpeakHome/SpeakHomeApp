package com.example.speakhomeapp

import Models.ApiResponse
import Models.PofileDevice.ProfileDeviceResource
import Services.ProfileDeviceService
import Services.ProfileService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    lateinit var profileDeviceService: ProfileDeviceService

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
            .baseUrl("http://192.168.1.47:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        profileDeviceService = retrofit.create<ProfileDeviceService>(ProfileDeviceService::class.java)

        getDevices()
    }
    private fun getDevices() {
        val userProfileId = (application as MyApplication).profile?.id
        if (userProfileId != null) {
            profileDeviceService.getAll().enqueue(object : Callback<ApiResponse<List<ProfileDeviceResource>>> {
                override fun onResponse(call: Call<ApiResponse<List<ProfileDeviceResource>>>, response: Response<ApiResponse<List<ProfileDeviceResource>>>) {
                    if (response.isSuccessful) {
                        val allDevices = response.body()?.content ?: emptyList()
                        // Filtrar los dispositivos que pertenecen al perfil logueado
                        val filteredDevices = allDevices.filter { it.profileId == userProfileId }

                        val recycler = findViewById<RecyclerView>(R.id.recyclerViewDevices)
                        recycler.layoutManager = LinearLayoutManager(applicationContext)
                        recycler.adapter = DeviceAdapter(filteredDevices)
                    } else {
                        Toast.makeText(this@HomeActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<ProfileDeviceResource>>>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "Fallo: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@HomeActivity, "No se encontró el ID del perfil.", Toast.LENGTH_SHORT).show()
        }
    }
}