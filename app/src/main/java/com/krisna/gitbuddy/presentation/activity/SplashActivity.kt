package com.krisna.gitbuddy.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.krisna.gitbuddy.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val themePreference = "theme_preference"
    private val themeMode = "theme_mode"

    private lateinit var binding: ActivitySplashBinding
    private val splashTime = 4000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }, splashTime)


        val preferences = getSharedPreferences(themePreference, Context.MODE_PRIVATE)
        val currentMode = preferences.getInt(themeMode, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(currentMode)
        setContentView(binding.root)
    }
}