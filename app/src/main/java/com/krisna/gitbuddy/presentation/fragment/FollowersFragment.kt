package com.krisna.gitbuddy.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.adapter.FollowersAdapter
import com.krisna.gitbuddy.databinding.FragmentFollowersBinding
import com.krisna.gitbuddy.presentation.DetailActivity
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class FollowersFragment : Fragment(), FollowersAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var githubViewModel: GithubViewModel
    private lateinit var adapterFollowers: FollowersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRvFollowers()

        githubViewModel = ViewModelProvider(requireActivity())[GithubViewModel::class.java]

        githubViewModel.clickedUsername.observe(viewLifecycleOwner) { username ->
            Log.d("savedUsername", "inFollowersFragment : $username")
            githubViewModel.getUserFollowers(username)
        }

        githubViewModel.userFollowers.observe(requireActivity()) { followers ->
            adapterFollowers.setFollowers(followers ?: emptyList())
        }
    }

    private fun setupRvFollowers() {
        adapterFollowers = FollowersAdapter(this)
        binding.rvFollowers.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterFollowers
        }
    }

    override fun onSearchItemClicked(username: String) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}