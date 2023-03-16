package com.krisna.gitbuddy.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.krisna.gitbuddy.presentation.fragment.FollowersFragment
import com.krisna.gitbuddy.presentation.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int) = when (position) {
        0 -> FollowersFragment()
        1 -> FollowingFragment()
        else -> throw IllegalArgumentException("Invalid position: $position")
    }

}