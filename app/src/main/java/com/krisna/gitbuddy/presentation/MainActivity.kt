package com.krisna.gitbuddy.presentation

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.adapter.SearchAdapter
import com.krisna.gitbuddy.adapter.UserAdapter
import com.krisna.gitbuddy.databinding.ActivityMainBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class MainActivity :
    AppCompatActivity(),
    UserAdapter.OnItemClickListener,
    SearchAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterUser: UserAdapter
    private lateinit var adapterSearch: SearchAdapter
    private lateinit var githubViewModel: GithubViewModel


    private lateinit var username :  String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        setupViewModelObservers()
    }

    private fun setupRecyclerView() {
        adapterUser = UserAdapter(this)
        adapterSearch = SearchAdapter(this)
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterUser
        }
    }

    private fun setupViewModelObservers() {
        githubViewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        githubViewModel.isLoading.observe(this) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        githubViewModel.allUser.observe(this) { allUser ->
            adapterUser.setData(allUser ?: emptyList())
        }

        githubViewModel.search.observe(this) { search ->
            adapterSearch.setSearch(search ?: emptyList())
            binding.rvUser.adapter = adapterSearch
        }

        githubViewModel.getAllUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                githubViewModel.searchUser(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    githubViewModel.searchUser(newText)
                }
                return false
            }
        })
        return true
    }

    override fun onItemUserListClicked(allUserResponseItem: Parcelable) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("clickedUser", allUserResponseItem)
        }
        startActivity(intent)
    }

    override fun onSearchItemClicked(searchResponseItem: Parcelable) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("clickedSearch", searchResponseItem)
        }
        startActivity(intent)
    }


}