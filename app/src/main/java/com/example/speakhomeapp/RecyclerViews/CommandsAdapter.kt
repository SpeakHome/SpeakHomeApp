package com.example.speakhomeapp.RecyclerViews

import Models.DeviceIot.CommandItem
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R

class CommandsAdapter(private val items: List<CommandItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_BUTTON = 0
        private const val TYPE_SEEK_BAR = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CommandItem.ButtonItem -> TYPE_BUTTON
            is CommandItem.SeekBarItem -> TYPE_SEEK_BAR
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
            TYPE_SEEK_BAR -> {
                val view = inflater.inflate(R.layout.item_seekbar, parent, false)
                SeekBarViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ButtonViewHolder -> {
                val buttonItem = items[position] as CommandItem.ButtonItem
                holder.bind(buttonItem.command)
                // Set OnClickListener here if necessary
            }
            is SeekBarViewHolder -> {
                val seekBarItem = items[position] as CommandItem.SeekBarItem
                holder.bind(seekBarItem.command,
                    object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            // Manejar cambios aquí si es necesario
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                            // Implementar si es necesario
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                            // Manejar el evento de parar la interacción aquí si es necesario
                        }
                    }
                ) { seekBarCommand ->
                    // Implementa la lógica del clic del botón de actualización aquí.
                    // Por ejemplo, enviar el valor del SeekBar usando seekBarCommand y el progreso actualizado.
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
