package com.krisna.gitbuddy.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.krisna.gitbuddy.databinding.FragmentFollowersBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var githubViewModel: GithubViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        githubViewModel = ViewModelProvider(requireActivity())[GithubViewModel::class.java]

        githubViewModel.clickedUsername.observe(viewLifecycleOwner) { username ->
            Log.d("savedUsername", "inFollowersFragment : $username")
            githubViewModel.getUserFollowers(username)
        }

        githubViewModel.userFollowers.observe(requireActivity()) { userFollowers ->
            binding.tvText.text = userFollowers?.get(0)?.login
        }
    }
}