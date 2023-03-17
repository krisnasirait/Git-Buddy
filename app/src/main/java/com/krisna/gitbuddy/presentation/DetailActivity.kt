package com.krisna.gitbuddy.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.krisna.gitbuddy.adapter.SectionsPagerAdapter
import com.krisna.gitbuddy.databinding.ActivityDetailBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var githubViewModel: GithubViewModel
    private var followersCount: Int = 0
    private var followingCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewpager
        val tabLayout = binding.tabs
        viewPager.adapter = sectionsPagerAdapter

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        githubViewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        val username = intent.getStringExtra("username")

        username?.let {
            githubViewModel.getUserDetail(it)
            githubViewModel.setClickedUsername(username)
            githubViewModel.getUserFollowers(username)
            githubViewModel.getUserFollowing(username)
        }

        githubViewModel.countFollowers.observe(this) { followersAmount ->
            followersCount = followersAmount ?: 0
            updateTabTitles(tabLayout, viewPager, followersCount, followingCount)
        }
        githubViewModel.countFollowing.observe(this) { followingAmount ->
            followingCount = followingAmount ?: 0
            updateTabTitles(tabLayout, viewPager, followersCount, followingCount)
        }

        githubViewModel.detailUser.observe(this) { data ->
            binding.tvName.text = data?.name
            binding.tvBio.text = data?.bio
            binding.tvEmail.text = data?.email
            Glide.with(binding.root)
                .load(data?.avatarUrl)
                .into(binding.cvProfile)
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

    private fun updateTabTitles(tabLayout: TabLayout, viewPager: ViewPager2, followersCount: Int, followingCount: Int) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "$followersCount Followers"
                1 -> tab.text = "$followingCount Following"
            }
        }.attach()
    }
}