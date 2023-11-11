package com.example.speakhomeapp.RecyclerViews

import Models.DeviceIot.CommandItem
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R
import com.google.android.material.slider.Slider

class CommandsAdapter(private val items: List<CommandItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_BUTTON = 0
        private const val TYPE_SLIDER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CommandItem.ButtonItem -> TYPE_BUTTON
            is CommandItem.SeekBarItem -> TYPE_SLIDER
            else -> throw IllegalArgumentException("Unknown type of CommandItem")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BUTTON -> {
                val view = inflater.inflate(R.layout.item_button, parent, false)
                ButtonViewHolder(view)
            }
            TYPE_SLIDER -> {
                val view = inflater.inflate(R.layout.item_slider, parent, false)
                SliderViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            // ... código para ButtonViewHolder ...

            is SliderViewHolder -> {
                val sliderItem = items[position] as CommandItem.SeekBarItem
                holder.bind(
                    sliderItem.command,
                    object : Slider.OnSliderTouchListener {
                        override fun onStartTrackingTouch(slider: Slider) {
                            // Implementar si es necesario
                        }

                        override fun onStopTrackingTouch(slider: Slider) {
                            // Manejar el evento de parar la interacción aquí si es necesario
                        }
                    },
                    Slider.OnChangeListener { slider, value, fromUser ->
                        // Manejar cambios aquí si es necesario
                    }
                ) { sliderCommand ->
                    // Implementa la lógica del clic del botón de actualización aquí.
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
