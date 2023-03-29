package com.krisna.gitbuddy.presentation.fragment

import android.animation.Animator
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        updateDescriptionText()

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

        return binding.root
    }

    private fun updateDescriptionText() {
        val textRes = if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            R.string.dark_mode
        } else {
            R.string.light_mode
        }
        binding.tvDescription.text = getString(textRes)
    }
}