package com.krisna.gitbuddy.presentation.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krisna.gitbuddy.data.repository.adapter.SearchAdapter
import com.krisna.gitbuddy.data.repository.adapter.UserAdapter
import com.krisna.gitbuddy.databinding.FragmentUserBinding
import com.krisna.gitbuddy.presentation.activity.DetailActivity
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class UserFragment : Fragment(), UserAdapter.OnItemClickListener, SearchAdapter.OnItemClickListener {

    private lateinit var binding: FragmentUserBinding
    private lateinit var githubViewModel: GithubViewModel
    private lateinit var adapter: RecyclerView.Adapter<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupSearchView()
        setupViewModelObservers()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(this)
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
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
                } else {
                    binding.rvUser.adapter = adapter
                }
                return false
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupViewModelObservers() {
        githubViewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        githubViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        githubViewModel.allUser.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        githubViewModel.search.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
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