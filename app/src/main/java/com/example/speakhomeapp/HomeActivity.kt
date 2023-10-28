package com.example.speakhomeapp

import Models.ApiResponse
import Models.Contact.ContactResource
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
        setupRecyclerView()
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
    private fun setupRecyclerView() {
        val userProfileId = (application as MyApplication).profile?.id

        if (userProfileId != null) {
            contactService.getAll().enqueue(object : Callback<ApiResponse<List<ContactResource>>> {
                override fun onResponse(call: Call<ApiResponse<List<ContactResource>>>, response: Response<ApiResponse<List<ContactResource>>>) {
                    if (response.isSuccessful) {
                        val contacts = response.body()?.content ?: emptyList()
                        val relatedProfileIds = contacts.filter {
                            it.profile1Id == userProfileId || it.profile2Id == userProfileId
                        }.map {
                            if (it.profile1Id == userProfileId) it.profile2Id else it.profile1Id
                        }.distinct()

                        profileService.getAll().enqueue(object : Callback<ApiResponse<List<ProfileResource>>> {
                            override fun onResponse(call: Call<ApiResponse<List<ProfileResource>>>, response: Response<ApiResponse<List<ProfileResource>>>) {
                                val profiles = response.body()?.content ?: emptyList()

                                val friendsProfiles = profiles.filter { profile ->
                                    relatedProfileIds.contains(profile.id) && profile.role.name != "tecnico"
                                }

                                val technicianProfiles = profiles.filter { profile ->
                                    relatedProfileIds.contains(profile.id) && profile.role.name == "tecnico"
                                }

                                val recyclerViewFriends: RecyclerView = findViewById(R.id.recyclerViewFriends)
                                recyclerViewFriends.layoutManager = LinearLayoutManager(this@HomeActivity)
                                recyclerViewFriends.adapter = ProfileAdapter(friendsProfiles)

                                val recyclerViewTechnicians: RecyclerView = findViewById(R.id.recyclerViewSupports)
                                recyclerViewTechnicians.layoutManager = LinearLayoutManager(this@HomeActivity)
                                recyclerViewTechnicians.adapter = ProfileAdapter(technicianProfiles)
                            }

                            override fun onFailure(call: Call<ApiResponse<List<ProfileResource>>>, t: Throwable) {
                            // Handle error
                                Log.e("NETWORK_ERROR", "Network error: ${t.message}", t)
                            }
                        })
                    } else {
                        // Handle error
                        Log.e("API_ERROR", "Failed to fetch contacts: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<ContactResource>>>, t: Throwable) {
                // Handle error
                    Log.e("NETWORK_ERROR", "Network error: ${t.message}", t)
                }
            })
        } else {
            // Show error message
            Toast.makeText(this@HomeActivity, "No se encontró el ID del perfil.", Toast.LENGTH_SHORT).show()
        }
    }

}