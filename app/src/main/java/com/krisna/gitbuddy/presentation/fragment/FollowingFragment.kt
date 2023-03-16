package com.krisna.gitbuddy.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.adapter.FollowingAdapter
import com.krisna.gitbuddy.databinding.FragmentFollowingBinding
import com.krisna.gitbuddy.presentation.DetailActivity
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class FollowingFragment : Fragment(), FollowingAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var githubViewModel: GithubViewModel
    private lateinit var adapterFollowing: FollowingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRvFollowing()

        githubViewModel = ViewModelProvider(requireActivity())[GithubViewModel::class.java]

        githubViewModel.clickedUsername.observe(viewLifecycleOwner) { username ->
            githubViewModel.getUserFollowing(username)
        }

        githubViewModel.userFollowing.observe(requireActivity()) {
            githubViewModel.userFollowing.observe(requireActivity()) { following ->
                adapterFollowing.setFollowing(following ?: emptyList())
            }
        }
    }

    private fun setupRvFollowing() {
        adapterFollowing = FollowingAdapter(this)
        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterFollowing
        }
    }

    override fun onFollowingIitemClicked(username: String) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}