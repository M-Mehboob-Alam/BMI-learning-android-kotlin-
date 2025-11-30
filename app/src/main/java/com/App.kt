package com

import android.app.Application
import com.example.bmilearningproject.util.initPrefs

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // initialize SharedPreferences globally
        initPrefs(this)
    }
}