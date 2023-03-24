package com.krisna.gitbuddy.data.repository.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.krisna.gitbuddy.data.model.response.followers.FollowersResponseItem
import com.krisna.gitbuddy.databinding.ItemFollowersFollowingBinding

class FollowersAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private val itemList = mutableListOf<FollowersResponseItem?>()

    inner class FollowersViewHolder(private val binding: ItemFollowersFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowersResponseItem) {
            binding.apply {
                tvUsername.text = item.login
                tvUserId.text = "${item.id}"
                Glide.with(root)
                    .load(item.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvProfile)
                root.setOnClickListener{
                    itemClickListener.onSearchItemClicked(item.login)
                }
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