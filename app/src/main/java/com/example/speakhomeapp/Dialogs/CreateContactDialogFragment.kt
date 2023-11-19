package com.example.speakhomeapp.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.speakhomeapp.R

class CreateContactDialogFragment(
    private val createListener: (String) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater
            val view = inflater.inflate(R.layout.dialog_create_contact, null)

            // Configurar botón para añadir un nuevo contacto
            view.findViewById<Button>(R.id.buttonAddContact).setOnClickListener {
                val userName = view.findViewById<EditText>(R.id.editTextContactUserName).text.toString()
                createListener(userName)
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
