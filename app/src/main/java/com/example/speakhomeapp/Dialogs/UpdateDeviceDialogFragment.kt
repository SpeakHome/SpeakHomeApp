package com.example.speakhomeapp.Dialogs

import Models.Device.UpdateDeviceResource
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.speakhomeapp.R

class UpdateDeviceDialogFragment(
    private val device: UpdateDeviceResource,
    private val updateListener: (UpdateDeviceResource) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater
            val view = inflater.inflate(R.layout.dialog_update_device, null)

            // Inicializar campos con los datos actuales del dispositivo
            view.findViewById<EditText>(R.id.editTextDeviceName).setText(device.name)
            view.findViewById<EditText>(R.id.editTextBaseUrl).setText(device.baseUrl)
            view.findViewById<EditText>(R.id.editTextDescription).setText(device.description)
            view.findViewById<EditText>(R.id.editTextPictureUrl).setText(device.pictureUrl)

            // Configurar el botón para actualizar la información del dispositivo
            view.findViewById<Button>(R.id.buttonEditDevice).setOnClickListener {
                val updatedDevice = UpdateDeviceResource(
                    name = view.findViewById<EditText>(R.id.editTextDeviceName).text.toString(),
                    baseUrl = view.findViewById<EditText>(R.id.editTextBaseUrl).text.toString(),
                    description = view.findViewById<EditText>(R.id.editTextDescription).text.toString(),
                    pictureUrl = view.findViewById<EditText>(R.id.editTextPictureUrl).text.toString()
                )
                updateListener(updatedDevice)
                dismiss()
            }

            // Configurar el botón de cancelar para cerrar el diálogo
            view.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
                dismiss()
            }

            // Crear el AlertDialog y establecer la vista personalizada
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
