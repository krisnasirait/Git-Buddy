package com.krisna.gitbuddy.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.data.repository.adapter.FollowingAdapter
import com.krisna.gitbuddy.databinding.FragmentFollowingBinding
import com.krisna.gitbuddy.presentation.activity.DetailActivity
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class FollowingFragment : Fragment(), FollowingAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFollowingBinding
    private val viewModel: GithubViewModel by activityViewModels()
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
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.clickedUsername.observe(viewLifecycleOwner) { username ->
            viewModel.getUserFollowers(username)
        }

        viewModel.userFollowing.observe(viewLifecycleOwner) { following ->
            if (following != null) {
                adapterFollowing.setFollowing(following)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
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