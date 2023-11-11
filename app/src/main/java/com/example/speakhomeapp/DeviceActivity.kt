package com.example.speakhomeapp

import Models.Device.DeviceResource
import Models.Device.UpdateDeviceResource
import Models.DeviceIot.ButtonCommand
import Models.DeviceIot.CommandItem
import Models.DeviceIot.SeekBarCommand
import Services.ButtonService
import Services.DeviceService
import Services.SeekBarService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.Dialogs.UpdateDeviceDialogFragment
import com.example.speakhomeapp.RecyclerViews.CommandsAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeviceActivity : AppCompatActivity() {
    lateinit var deviceService: DeviceService
    lateinit var buttonService: ButtonService
    lateinit var seekBarService: SeekBarService
    lateinit var deviceResource: DeviceResource
    var commandItems = mutableListOf<CommandItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_view)

        // Obtener una referencia al TextView
        val textViewUserName = findViewById<TextView>(R.id.textViewUserName)
        val buttonEditDevice = findViewById<Button>(R.id.buttonEditDevice)
        val buttonDeleteDevice = findViewById<Button>(R.id.buttonDeleteDevice)

        // Obtener el ID del dispositivo desde el Intent
        val deviceId = intent.getLongExtra("DEVICE_ID", 0) // 0 sería un valor por defecto si no se encuentra el extra

        // Obtener la aplicación y acceder a los datos de la sesión
        val app = application as MyApplication
        val userProfile = app.profile

        // Asignar el nombre del usuario al TextView
        textViewUserName.text = userProfile?.userName

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.47:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        deviceService = retrofit.create<DeviceService>(DeviceService::class.java)

        val fireBaseRetrofit = Retrofit.Builder()
            .baseUrl("https://dispositivo-iot-cec65-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        buttonService = fireBaseRetrofit.create<ButtonService>(ButtonService::class.java)
        seekBarService = fireBaseRetrofit.create<SeekBarService>(SeekBarService::class.java)

        getDevice(deviceId)
        getCommands()

        buttonEditDevice.setOnClickListener {
            showUpdateDeviceDialog()
        }
        buttonDeleteDevice.setOnClickListener{
            onDeleteDeviceClicked()
        }
    }
    private fun getDevice(deviceId:Long){
        val deviceName = findViewById<TextView>(R.id.textViewDeviceName)
        val deviceDescription = findViewById<TextView>(R.id.textViewDeviceDescription)
        val deviceBaseUrl = findViewById<TextView>(R.id.textViewDeviceBaseUrl)
        val deviceStatus = findViewById<TextView>(R.id.textViewDeviceStatus)
        val devicePicture = findViewById<ImageView>(R.id.imageViewDevicePicture)

        val userProfileId = (application as MyApplication).profile?.id
        val isAuthenticated = (application as MyApplication).isAuthenticated
        if (userProfileId != null && isAuthenticated) {
            deviceService.getById(deviceId.toInt()).enqueue(object : Callback<DeviceResource>{
                override fun onResponse(call: Call<DeviceResource>, response: Response<DeviceResource>) {
                    if (response.isSuccessful) {
                        deviceResource= response.body()!!
                        deviceName.text=deviceResource.name
                        deviceDescription.text=deviceResource.description
                        deviceBaseUrl.text=deviceResource.baseUrl
                        deviceStatus.text=deviceResource.deviceStatus.name
                        Picasso.get().load(deviceResource.pictureUrl)
                            .resize(250,250)
                            .centerCrop().into(devicePicture)
                    } else {
                        Toast.makeText(this@DeviceActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DeviceResource>, t: Throwable) {
                    Toast.makeText(this@DeviceActivity, "Fallo: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else {
            Toast.makeText(this@DeviceActivity, "No se encontró el ID del perfil.", Toast.LENGTH_SHORT).show()
        }
    }

    // Ejemplo de cómo podrías obtener los comandos, ajusta los nombres de métodos y tipos según tu implementación.
    fun getCommands() {
        // Suponiendo que tienes un servicio que obtiene los ButtonCommands y otro para los SeekBarCommands
        buttonService.getAll().enqueue(object : Callback<List<ButtonCommand>> {
            override fun onResponse(call: Call<List<ButtonCommand>>, response: Response<List<ButtonCommand>>) {
                if (response.isSuccessful) {
                    val buttonCommands = response.body()?.map { CommandItem.ButtonItem(it) } ?: emptyList()
                    updateCommands(buttonCommands)
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<ButtonCommand>>, t: Throwable) {
                // Handle failure
            }
        })

        seekBarService.getAll().enqueue(object : Callback<List<SeekBarCommand>> {
            override fun onResponse(call: Call<List<SeekBarCommand>>, response: Response<List<SeekBarCommand>>) {
                if (response.isSuccessful) {
                    val seekBarCommands = response.body()?.map { CommandItem.SeekBarItem(it) } ?: emptyList()
                    updateCommands(seekBarCommands)
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<SeekBarCommand>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun updateCommands(newItems: List<CommandItem>) {
        val recyclerViewCommands: RecyclerView = findViewById(R.id.recyclerViewCommands)

        if (recyclerViewCommands.adapter == null) {
            // Si el adapter no está configurado, lo hacemos aquí.
            commandItems.addAll(newItems)
            recyclerViewCommands.layoutManager = LinearLayoutManager(this)
            recyclerViewCommands.adapter = CommandsAdapter(commandItems)
        } else {
            // Aquí nos aseguramos de que no haya elementos duplicados.
            val adapter = recyclerViewCommands.adapter as CommandsAdapter
            val currentSize = commandItems.size
            commandItems.addAll(newItems)
            adapter.notifyItemRangeInserted(currentSize, commandItems.size)
        }
    }

    private fun updateDevice(updatedDevice: UpdateDeviceResource) {
        // Aquí implementarías la llamada a Retrofit para actualizar el dispositivo
        deviceService.update(deviceResource.id.toInt(), updatedDevice).enqueue(object : Callback<DeviceResource> {
            override fun onResponse(call: Call<DeviceResource>, response: Response<DeviceResource>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DeviceActivity, "Device updated successfully!", Toast.LENGTH_SHORT).show()
                    // Actualiza la UI con los datos del dispositivo actualizados
                    getDevice(deviceResource.id)
                } else {
                    Toast.makeText(this@DeviceActivity, "Update failed :v: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DeviceResource>, t: Throwable) {
                Toast.makeText(this@DeviceActivity, "Update failed: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun showUpdateDeviceDialog() {
        // Crear una instancia del UpdateDeviceResource a partir del DeviceResource
        val updateDeviceResource = UpdateDeviceResource(
            name = deviceResource.name,
            baseUrl = deviceResource.baseUrl,
            description = deviceResource.description,
            pictureUrl = deviceResource.pictureUrl
        )

        // Crear una instancia del UpdateDeviceDialogFragment
        val dialogFragment = UpdateDeviceDialogFragment(updateDeviceResource) { updatedDevice ->
            updateDevice(updatedDevice)
        }

        // Mostrar el dialog fragment
        dialogFragment.show(supportFragmentManager, "UpdateDeviceDialogFragment")
    }

    fun onDeleteDeviceClicked() {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Dispositivo")
            .setMessage("¿Estás seguro de que quieres eliminar este dispositivo?")
            .setPositiveButton("Eliminar") { dialog, which ->
                deleteDevice()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    fun deleteDevice() {
        deviceService.delete(deviceResource.id.toInt()).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DeviceActivity, "Dispositivo eliminado correctamente.", Toast.LENGTH_SHORT).show()
                    // Actualizar la UI aquí, por ejemplo, eliminar el item del RecyclerView
                } else {
                    Toast.makeText(this@DeviceActivity, "Error al eliminar el dispositivo: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DeviceActivity, "Fallo al eliminar el dispositivo: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}