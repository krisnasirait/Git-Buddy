package com.krisna.gitbuddy.presentation

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.adapter.SectionsPagerAdapter
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponseItem
import com.krisna.gitbuddy.data.model.response.detail.DetailUserResponse
import com.krisna.gitbuddy.data.model.response.search.SearchResponseItem
import com.krisna.gitbuddy.databinding.ActivityDetailBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.activity_detail.*

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

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        githubViewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        intent?.getParcelableExtra<AllUserResponseItem>("clickedUser")?.apply {
            githubViewModel.getUserDetail(login)
        }

        intent?.getParcelableExtra<SearchResponseItem>("clickedSearch")?.apply {
            githubViewModel.getUserDetail(login)
        }

        githubViewModel.detailUser.observe(this) { data->
            binding.tvName.text = data?.name
            binding.tvBio.text = data?.bio
            binding.tvEmail.text = data?.email
            Glide.with(binding.root)
                .load(data?.avatarUrl)
                .into(binding.cvProfile)
        }

    }
}