package com.example.speakhomeapp.RecyclerViews

import Models.DeviceIot.ButtonCommand
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R

class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val button: Button = itemView.findViewById(R.id.buttonCommand)

    fun bind(item: ButtonCommand) {
        button.text = item.name
        // Aquí podrías establecer un OnClickListener para el botón, etc.
    }
}