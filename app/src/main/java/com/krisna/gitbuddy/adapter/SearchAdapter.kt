package com.krisna.gitbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.gitbuddy.data.model.Search
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponseItem
import com.krisna.gitbuddy.databinding.ItemUserRvBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.UserViewHolder>() {

    private val itemList = mutableListOf<Any>()

    inner class UserViewHolder(private val binding: ItemUserRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {
            if (item is Search) {
                binding.tvName.text = item.login
                binding.tvId.text = item.id.toString()
                Glide.with(binding.root)
                    .load(item.avatarUrl)
                    .into(binding.cvProfilePict)
            } else if (item is AllUserResponseItem) {
                binding.tvName.text = item.login
                binding.tvId.text = item.id.toString()
                Glide.with(binding.root)
                    .load(item.avatarUrl)
                    .into(binding.cvProfilePict)
            }
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
        return itemList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Any>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSearch(data: List<Search>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }


}