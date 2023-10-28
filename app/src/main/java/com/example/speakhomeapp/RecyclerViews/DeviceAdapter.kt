package com.example.speakhomeapp.RecyclerViews

import Models.PofileDevice.ProfileDeviceResource
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R

class DeviceAdapter(val deviceList: List<ProfileDeviceResource>) : RecyclerView.Adapter<DeviceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DeviceViewHolder(layoutInflater.inflate(R.layout.device_card,parent,false))
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = deviceList[position]
        holder.render(device)
        // Aquí puedes setear más datos en los elementos visuales
    }

    override fun getItemCount() = deviceList.size
}