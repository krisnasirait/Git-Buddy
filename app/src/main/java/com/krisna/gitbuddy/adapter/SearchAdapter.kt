package com.krisna.gitbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.gitbuddy.data.model.Search
import com.krisna.gitbuddy.databinding.ItemUserRvBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.UserViewHolder>() {

    private val itemUserResponse = mutableListOf<Search?>()

    inner class UserViewHolder(private val binding: ItemUserRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Search) {
            binding.tvName.text = item.login

            val regex = "[^/]+$".toRegex()
            val matchResult = regex.find(item.url)

            binding.tvUsername.text = matchResult!!.value
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.cvProfilePict)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return itemUserResponse.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        itemUserResponse[position]?.let { holder.bind(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataUser(search: List<Search?>) {
        itemUserResponse.clear()
        itemUserResponse.addAll(search)
        notifyDataSetChanged()
    }


}