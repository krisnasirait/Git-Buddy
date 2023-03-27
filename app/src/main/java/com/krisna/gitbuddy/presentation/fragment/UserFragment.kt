package com.krisna.gitbuddy.presentation.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.data.repository.adapter.SearchAdapter
import com.krisna.gitbuddy.data.repository.adapter.UserAdapter
import com.krisna.gitbuddy.databinding.FragmentUserBinding
import com.krisna.gitbuddy.presentation.activity.DetailActivity
import com.krisna.gitbuddy.presentation.activity.MainActivity
import com.krisna.gitbuddy.presentation.activity.SettingsActivity
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class UserFragment : Fragment(), UserAdapter.OnItemClickListener, SearchAdapter.OnItemClickListener {

    private lateinit var binding: FragmentUserBinding
    private lateinit var githubViewModel: GithubViewModel
    private lateinit var adapterUser: UserAdapter
    private lateinit var adapterSearch: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupViewModelObservers()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapterUser = UserAdapter(this)
        adapterSearch = SearchAdapter(this)
        binding.rvUser.apply {
            layoutManager =  LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterUser
        }
    }

    private fun setupViewModelObservers() {
        githubViewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        githubViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        githubViewModel.allUser.observe(viewLifecycleOwner) { allUser ->
            adapterUser.setData(allUser ?: emptyList())
        }

        githubViewModel.search.observe(viewLifecycleOwner) { search ->
            adapterSearch.setSearch(search ?: emptyList())
            binding.rvUser.adapter = adapterSearch
        }

        githubViewModel.getAllUsers()
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        val settings = menu.findItem(R.id.setting)
        settings.setOnMenuItemClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
            true
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                (activity as MainActivity).getGithubViewModel().searchUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    (activity as MainActivity).getGithubViewModel().searchUser(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
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