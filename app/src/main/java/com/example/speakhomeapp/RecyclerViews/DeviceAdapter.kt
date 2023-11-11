package com.example.speakhomeapp.RecyclerViews

import Models.Device.DeviceResource
import Models.PofileDevice.ProfileDeviceResource
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.DeviceActivity
import com.example.speakhomeapp.R

class DeviceAdapter(private val deviceList: List<DeviceResource>, val context: Context) : RecyclerView.Adapter<DeviceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DeviceViewHolder(layoutInflater.inflate(R.layout.device_card,parent,false))
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = deviceList[position]
        holder.render(device)

        // Añadir OnClickListener
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeviceActivity::class.java)

            // Suponiendo que DeviceResource tiene un id o algún identificador único
            intent.putExtra("DEVICE_ID", device.id)

            // Puedes agregar datos adicionales si es necesario
            // intent.putExtra("DEVICE_NAME", device.name)

            context.startActivity(intent)
        }

        // Aquí puedes setear más datos en los elementos visuales
    }

    override fun getItemCount() = deviceList.size
}