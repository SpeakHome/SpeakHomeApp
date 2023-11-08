package com.example.speakhomeapp.RecyclerViews

import Models.DeviceIot.SeekBarCommand
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R

class SeekBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val label: TextView = itemView.findViewById(R.id.seekBarLabel)
    private val seekBar: SeekBar = itemView.findViewById(R.id.seekBarCommand)
    private val updateButton: Button = itemView.findViewById(R.id.buttonUpdateCommand)

    fun bind(item: SeekBarCommand, onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener, onUpdateButtonClickListener: (SeekBarCommand) -> Unit) {
        label.text = item.name

        // Establecer un OnSeekBarChangeListener
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)

        // Configurar el botón de actualización
        updateButton.setOnClickListener {
            onUpdateButtonClickListener(item)
        }
    }
}
