package com.krisna.gitbuddy.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.data.repository.adapter.SectionsPagerAdapter
import com.krisna.gitbuddy.databinding.ActivityDetailBinding
import com.krisna.gitbuddy.di.ViewModelFactory
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val githubViewModel: GithubViewModel by viewModels(
        factoryProducer = {
            ViewModelFactory.getInstance(this)
        }
    )
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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    private fun setupViewModel() {
        val username = intent.getStringExtra("username")
        username?.let {
            githubViewModel.getUserDetail(it)
            githubViewModel.setClickedUsername(username)
            githubViewModel.getUserFollowers(username)
            githubViewModel.getUserFollowing(username)
        }
    }

    private fun setupTabLayout() {
        binding.viewpager.adapter = SectionsPagerAdapter(this)
    }

    private fun observeData() {
        githubViewModel.isLoading.observe(this) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        githubViewModel.detailUser.observe(this) { data ->
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