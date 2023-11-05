package com.example.speakhomeapp

import Models.ApiResponse
import Models.Contact.ContactResource
import Models.Device.DeviceResource
import Models.PofileDevice.ProfileDeviceResource
import Models.Profile.ProfileResource
import Services.ContactService
import Services.ProfileDeviceService
import Services.ProfileService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.RecyclerViews.DeviceAdapter
import com.example.speakhomeapp.RecyclerViews.ProfileAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    lateinit var profileDeviceService: ProfileDeviceService
    lateinit var contactService: ContactService
    lateinit var profileService: ProfileService
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
        contactService = retrofit.create<ContactService>(ContactService::class.java)
        profileService = retrofit.create<ProfileService>(ProfileService::class.java)
        getDevices()
        getContacts()
    }
    private fun getDevices() {
        val userProfileId = (application as MyApplication).profile?.id
        if (userProfileId != null) {
            profileDeviceService.getDevicesByProfileId(userProfileId).enqueue(object : Callback<ApiResponse<List<DeviceResource>>> {
                override fun onResponse(call: Call<ApiResponse<List<DeviceResource>>>, response: Response<ApiResponse<List<DeviceResource>>>) {
                    if (response.isSuccessful) {
                        val devices = response.body()?.content ?: emptyList()

                        val recycler = findViewById<RecyclerView>(R.id.recyclerViewDevices)
                        recycler.layoutManager = LinearLayoutManager(applicationContext)
                        recycler.adapter = DeviceAdapter(devices)
                    } else {
                        Toast.makeText(this@HomeActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<DeviceResource>>>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "Fallo: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@HomeActivity, "No se encontró el ID del perfil.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getContacts() {
        val userProfileId = (application as MyApplication).profile?.id

        userProfileId?.let { profileId ->
            // Obtener contactos que son técnicos

            contactService.getTechnicianContactsByProfileId(profileId.toInt()).enqueue(object : Callback<ApiResponse<List<ContactResource>>> {
                override fun onResponse(call: Call<ApiResponse<List<ContactResource>>>, response: Response<ApiResponse<List<ContactResource>>>) {
                    if (response.isSuccessful) {
                        // Aquí manejamos la respuesta para técnicos
                        val technicianContacts = response.body()?.content ?: emptyList()
                        updateTechniciansRecyclerView(technicianContacts)
                    } else {
                        Toast.makeText(this@HomeActivity, "Error técnicos: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ApiResponse<List<ContactResource>>>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "Fallo técnicos: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })

            // Obtener contactos que no son técnicos
            contactService.getNonTechnicianContactsByProfileId(profileId.toInt()).enqueue(object : Callback<ApiResponse<List<ContactResource>>> {
                override fun onResponse(call: Call<ApiResponse<List<ContactResource>>>, response: Response<ApiResponse<List<ContactResource>>>) {
                    if (response.isSuccessful) {
                        // Aquí manejamos la respuesta para no técnicos
                        val nonTechnicianContacts = response.body()?.content ?: emptyList()
                        updateNonTechniciansRecyclerView(nonTechnicianContacts)
                    } else {
                        Toast.makeText(this@HomeActivity, "Error no técnicos: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ApiResponse<List<ContactResource>>>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "Fallo no técnicos: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        } ?: run {
            Toast.makeText(this@HomeActivity, "No se encontró el ID del perfil.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTechniciansRecyclerView(technicians: List<ContactResource>) {
        // Suponiendo que cada ContactResource contiene un ProfileResource, necesitas extraer los ProfileResource
        val technicianProfiles = technicians.map { it.contactProfile } // Reemplaza 'profileResource' con el nombre real de tu propiedad.

        val recyclerViewTechnicians: RecyclerView = findViewById(R.id.recyclerViewSupports)
        recyclerViewTechnicians.layoutManager = LinearLayoutManager(this@HomeActivity)
        recyclerViewTechnicians.adapter = ProfileAdapter(technicianProfiles)
    }

    private fun updateNonTechniciansRecyclerView(nonTechnicians: List<ContactResource>) {
        // Igual que con los técnicos, extraemos los ProfileResource de los ContactResource
        val nonTechnicianProfiles = nonTechnicians.map { it.contactProfile } // Reemplaza 'profileResource' con el nombre real de tu propiedad.

        val recyclerViewFriends: RecyclerView = findViewById(R.id.recyclerViewFriends)
        recyclerViewFriends.layoutManager = LinearLayoutManager(this@HomeActivity)
        recyclerViewFriends.adapter = ProfileAdapter(nonTechnicianProfiles)
    }
}