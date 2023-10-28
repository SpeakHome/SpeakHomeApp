package com.example.speakhomeapp.RecyclerViews

import Models.PofileDevice.ProfileDeviceResource
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R
import com.squareup.picasso.Picasso

class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val deviceName = view.findViewById<TextView>(R.id.textViewDeviceName)
    val deviceLocation = view.findViewById<TextView>(R.id.textViewDeviceLocation)
    val devicePicture = view.findViewById<ImageView>(R.id.imageViewDevicePicture)
    fun render(profileDeviceResource: ProfileDeviceResource){
        deviceName.text=profileDeviceResource.device.name
        deviceLocation.text=profileDeviceResource.device.location.description
        Picasso.get().load(profileDeviceResource.device.pictureUrl)
            .resize(250,250)
            .centerCrop().into(devicePicture)
    }
}