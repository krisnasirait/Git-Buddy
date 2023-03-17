package com.krisna.gitbuddy.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.adapter.FollowersAdapter
import com.krisna.gitbuddy.databinding.FragmentFollowersBinding
import com.krisna.gitbuddy.presentation.DetailActivity
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class FollowersFragment : Fragment(), FollowersAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFollowersBinding
    private val viewModel: GithubViewModel by activityViewModels()
    private val adapter = FollowersAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvFollowers.adapter = adapter
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewModel.clickedUsername.observe(viewLifecycleOwner) { username ->
            viewModel.getUserFollowers(username)
        }

        viewModel.userFollowers.observe(viewLifecycleOwner) { followers ->
            if (followers != null) {
                adapter.setFollowers(followers)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onSearchItemClicked(username: String) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}