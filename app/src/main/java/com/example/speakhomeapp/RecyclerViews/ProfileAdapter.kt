package com.example.speakhomeapp.RecyclerViews

import Models.Profile.ProfileResource
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.ChatActivity
import com.example.speakhomeapp.DeviceActivity
import com.example.speakhomeapp.HomeActivity
import com.example.speakhomeapp.R

class ProfileAdapter(private val profiles: List<ProfileResource>, val context: Context) : RecyclerView.Adapter<ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProfileViewHolder(layoutInflater.inflate(R.layout.profile_card, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = profiles[position]
        holder.bind(profile)
        // Añadir OnClickListener
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)

            // Suponiendo que ProfileResource tiene un id o algún identificador único
            intent.putExtra("CONTACT_ID", profile.id)

            context.startActivity(intent)
        }
    }
    override fun getItemCount() = profiles.size
}