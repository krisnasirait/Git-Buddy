package com.krisna.gitbuddy.presentation

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.adapter.SearchAdapter
import com.krisna.gitbuddy.adapter.UserAdapter
import com.krisna.gitbuddy.databinding.ActivityMainBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener, SearchAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterUser: UserAdapter
    private lateinit var adapterSearch: SearchAdapter
    private lateinit var githubViewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterUser = UserAdapter(this)
        adapterSearch = SearchAdapter(this)
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterUser
        }

        githubViewModel = ViewModelProvider(this)[GithubViewModel::class.java]

        githubViewModel.isLoading.observe(this) { isLoading ->
            binding.lvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        githubViewModel.getAllUsers()

        githubViewModel.allUser.observe(this) { allUser ->
            if (allUser != null) {
                adapterUser.setData(allUser)
                adapterUser.notifyDataSetChanged()
            }
        }



        githubViewModel.users.observe(this) { users ->
            if (users != null) {
                adapterSearch.setSearch(users)
                adapterSearch.notifyDataSetChanged()
            }
            binding.rvUser.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = adapterSearch
            }
        }

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
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
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

    override fun onItemClick(article: Parcelable) {
        TODO("Not yet implemented")
    }

}