package com.example.speakhomeapp.RecyclerViews

import Models.Profile.ProfileResource
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speakhomeapp.R

class ProfileAdapter(private val profiles: List<ProfileResource>) : RecyclerView.Adapter<ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.profile_card, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = profiles[position]
        holder.bind(profile)
    }

    override fun getItemCount(): Int = profiles.size
}