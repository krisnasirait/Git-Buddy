package com.krisna.gitbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.gitbuddy.data.model.response.followers.FollowersResponseItem
import com.krisna.gitbuddy.databinding.ItemFollowersFollowingBinding

class FollowersAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private val itemList = mutableListOf<FollowersResponseItem?>()

    inner class FollowersViewHolder(private val binding: ItemFollowersFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowersResponseItem) {
            binding.tvUsername.text = item.login
            binding.tvUserId.text = item.id.toString()
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.cvProfile)
            binding.root.setOnClickListener{
                itemClickListener.onSearchItemClicked(item.login)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        return FollowersViewHolder(
            ItemFollowersFollowingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        itemList[position]?.let {
            holder.bind(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFollowers(data: List<FollowersResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onSearchItemClicked(username: String)
    }


}