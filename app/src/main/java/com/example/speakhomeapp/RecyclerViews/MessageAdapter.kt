package com.example.speakhomeapp.RecyclerViews

import Models.ContactMessageResource.ContactMessageResource
import Models.Message.MessageResource
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R

class MessageAdapter(private val userProfileId: Long, private var contactMessages: List<ContactMessageResource>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            val view = layoutInflater.inflate(R.layout.item_message_profile, parent, false)
            MessageSentViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.item_message_contact, parent, false)
            MessageReceivedViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contactMessage = contactMessages[position]
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as MessageSentViewHolder).bind(contactMessage)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as MessageReceivedViewHolder).bind(contactMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val contactMessage = contactMessages[position]
        return if (contactMessage.contact.profileId == userProfileId) {
            VIEW_TYPE_MESSAGE_SENT
        } else {
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun getItemCount() = contactMessages.size
}