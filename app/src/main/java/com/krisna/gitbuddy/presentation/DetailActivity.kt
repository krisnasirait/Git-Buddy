package com.krisna.gitbuddy.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.adapter.SectionsPagerAdapter
import com.krisna.gitbuddy.databinding.ActivityDetailBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var githubViewModel: GithubViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewpager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        githubViewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        val username = intent.getStringExtra("username")

        username?.let {
            githubViewModel.getUserDetail(it)
            githubViewModel.setClickedUsername(username)
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
}