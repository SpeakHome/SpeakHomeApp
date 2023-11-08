package com.example.speakhomeapp.Dialogs

import Models.Device.CreateDeviceResource
import Models.Location.LocationResource
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.speakhomeapp.R

class CreateDeviceDialogFragment(
    private val locations: List<LocationResource>,
    private val createListener: (CreateDeviceResource) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater
            val view = inflater.inflate(R.layout.dialog_create_device, null)

            // Configurar el Spinner con las ubicaciones
            val spinnerLocation: Spinner = view.findViewById(R.id.spinnerLocation)
            val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, locations.map { it.name })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerLocation.adapter = adapter

            // Configurar botón para crear un nuevo dispositivo
            view.findViewById<Button>(R.id.buttonCreateDevice).setOnClickListener {
                val selectedLocation = locations[spinnerLocation.selectedItemPosition]
                val newDevice = CreateDeviceResource(
                    name = view.findViewById<EditText>(R.id.editTextDeviceName).text.toString(),
                    baseUrl = view.findViewById<EditText>(R.id.editTextBaseUrl).text.toString(),
                    description = view.findViewById<EditText>(R.id.editTextDescription).text.toString(),
                    pictureUrl = view.findViewById<EditText>(R.id.editTextPictureUrl).text.toString(),
                    locationId = selectedLocation.id,
                    deviceStatusId = 1L // Suponiendo que los IDs son de tipo Long
                )
                createListener(newDevice)
                dismiss()
            }

            // Configurar botón de cancelar para cerrar el diálogo
            view.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
                dismiss()
            }

            // Crear el AlertDialog y establecer la vista personalizada
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
