package com.krisna.gitbuddy.presentation.activity

import android.animation.Animator
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateDescriptionText()
        setupActionBar()

        binding.lvLightDarkMode.setOnClickListener {
            val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            val minFrame = 60
            val maxFrame = 134
            val speed = if (isDarkMode) -1f else 1f

            binding.lvLightDarkMode.setMinFrame(minFrame)
            binding.lvLightDarkMode.setMaxFrame(maxFrame)
            binding.lvLightDarkMode.speed = speed
            binding.lvLightDarkMode.playAnimation()

            binding.lvLightDarkMode.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {}

                override fun onAnimationEnd(p0: Animator) {
                    val nightMode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
                    AppCompatDelegate.setDefaultNightMode(nightMode)
                    updateDescriptionText()
                }

                override fun onAnimationCancel(p0: Animator) {}
                override fun onAnimationRepeat(p0: Animator) {}
            })
        }
    }

    private fun updateDescriptionText() {
        val textRes = if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            R.string.dark_mode
        } else {
            R.string.light_mode
        }
        binding.tvDescription.text = getString(textRes)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            elevation = 0f
            title = getString(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}