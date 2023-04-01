package com.krisna.gitbuddy.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.gitbuddy.data.repository.adapter.FavoriteAdapter
import com.krisna.gitbuddy.databinding.FragmentFavoriteBinding
import com.krisna.gitbuddy.di.ViewModelFactory
import com.krisna.gitbuddy.presentation.activity.DetailActivity
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class FavoriteFragment : Fragment(), FavoriteAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val githubViewModel: GithubViewModel by activityViewModels(
        factoryProducer = {
            ViewModelFactory.getInstance(requireContext())
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModelObservers()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(this)
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
    }

    private fun setupViewModelObservers() {
        githubViewModel.getAllFavoriteUser()

        githubViewModel.userFavorite.observe(viewLifecycleOwner) {
            favoriteAdapter.setFavorite(it ?: emptyList())
        }
    }

    override fun onFavoriteItemClicked(username: String) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }


}