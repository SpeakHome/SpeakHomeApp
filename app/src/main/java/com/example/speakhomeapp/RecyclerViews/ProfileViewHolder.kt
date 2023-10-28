package com.example.speakhomeapp.RecyclerViews

import Models.Profile.ProfileResource
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R

class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val profileName: TextView = view.findViewById(R.id.textViewProfileName)

    fun bind(profile: ProfileResource) {
        profileName.text = profile.userName // Asumiendo que `ProfileResource` tiene un atributo `name`.
    }
}