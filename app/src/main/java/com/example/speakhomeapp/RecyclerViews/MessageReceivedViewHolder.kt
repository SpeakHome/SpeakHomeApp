package com.example.speakhomeapp.RecyclerViews

import Models.ContactMessageResource.ContactMessageResource
import Models.Message.MessageResource
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R

class MessageReceivedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val messageTextView: TextView = view.findViewById(R.id.textViewContactMessage)

    fun bind(contactMessageResource: ContactMessageResource) {
        messageTextView.text = contactMessageResource.message.content
    }
}