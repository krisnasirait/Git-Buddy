package com.krisna.gitbuddy.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.data.repository.adapter.SearchAdapter
import com.krisna.gitbuddy.data.repository.adapter.UserAdapter
import com.krisna.gitbuddy.databinding.FragmentUserBinding
import com.krisna.gitbuddy.presentation.activity.DetailActivity
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class UserFragment : Fragment(), UserAdapter.OnItemClickListener,
    SearchAdapter.OnItemClickListener {

    private lateinit var binding: FragmentUserBinding
    private lateinit var githubViewModel: GithubViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupSearchView()
        setupViewModelObservers()
        return binding.root
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(this)
        searchAdapter = SearchAdapter(this)
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                githubViewModel.searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    githubViewModel.searchUser(newText)
                }
                return false
            }
        })
    }

    private fun setupViewModelObservers() {
        githubViewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        githubViewModel.isLoading.observe(requireActivity()) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        githubViewModel.allUser.observe(requireActivity()) {
            userAdapter.setData(it ?: emptyList())
        }

        githubViewModel.search.observe(requireActivity()) {
            searchAdapter.setSearch(it ?: emptyList())
            binding.rvUser.adapter = searchAdapter
        }

        githubViewModel.getAllUsers()
    }

    override fun onItemUserListClicked(username: String) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }

    override fun onSearchItemClicked(username: String) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}