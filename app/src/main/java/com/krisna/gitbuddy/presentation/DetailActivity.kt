package com.krisna.gitbuddy.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.krisna.gitbuddy.presentation.adapter.SectionsPagerAdapter
import com.krisna.gitbuddy.databinding.ActivityDetailBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: GithubViewModel
    private var followersCount: Int = 0
    private var followingCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupViewModel()
        setupTabLayout()
        observeData()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            elevation = 0f
            title = "Detail User"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[GithubViewModel::class.java]
        val username = intent.getStringExtra("username")
        username?.let {
            viewModel.getUserDetail(it)
            viewModel.setClickedUsername(username)
            viewModel.getUserFollowers(username)
            viewModel.getUserFollowing(username)
        }
    }

    private fun setupTabLayout() {
        binding.viewpager.adapter = SectionsPagerAdapter(this)
    }

    private fun observeData() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.detailUser.observe(this) { data ->
            binding.tvName.text = data?.name
            binding.tvBio.text = data?.bio
            binding.tvUsername.text = data?.login
            followersCount = data?.followers ?: 0
            followingCount = data?.following ?: 0
            updateTabTitles()
            Glide.with(binding.root)
                .load(data?.avatarUrl)
                .into(binding.cvProfile)
        }
    }

    private fun updateTabTitles() {
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "$followersCount Followers"
                1 -> tab.text = "$followingCount Following"
            }
        }.attach()
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