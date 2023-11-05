package com.example.speakhomeapp.RecyclerViews

import Models.Device.DeviceResource
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
    val deviceDescription = view.findViewById<TextView>(R.id.textViewDeviceDescription)
    val devicePicture = view.findViewById<ImageView>(R.id.imageViewDevicePicture)
    fun render(deviceResource: DeviceResource){
        deviceName.text=deviceResource.name
        deviceLocation.text=deviceResource.location.name
        deviceDescription.text=deviceResource.description
        Picasso.get().load(deviceResource.pictureUrl)
            .resize(250,250)
            .centerCrop().into(devicePicture)
    }
}