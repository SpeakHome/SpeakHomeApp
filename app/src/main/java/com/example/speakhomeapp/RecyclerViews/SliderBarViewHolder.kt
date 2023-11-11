package com.example.speakhomeapp.RecyclerViews

import Models.DeviceIot.SeekBarCommand
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R
import com.google.android.material.slider.Slider

class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val label: TextView = itemView.findViewById(R.id.sliderLabel)
    private val slider: Slider = itemView.findViewById(R.id.sliderCommand)
    private val updateButton: Button = itemView.findViewById(R.id.buttonUpdateCommand)

    fun bind(item: SeekBarCommand, onSliderTouchListener: Slider.OnSliderTouchListener, onSliderChangeListener: Slider.OnChangeListener, onUpdateButtonClickListener: (SeekBarCommand) -> Unit) {
        label.text = item.name

        // Establecer un OnSliderTouchListener y OnChangeListener
        slider.addOnSliderTouchListener(onSliderTouchListener)
        slider.addOnChangeListener(onSliderChangeListener)

        // Configurar el botón de actualización
        updateButton.setOnClickListener {
            onUpdateButtonClickListener(item)
        }
    }
}
