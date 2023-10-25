package com.example.speakhomeapp

import Models.Profile.ProfileResource
import android.app.Application

class MyApplication : Application() {
    var profile: ProfileResource? = null
    var isAuthenticated: Boolean = false
}