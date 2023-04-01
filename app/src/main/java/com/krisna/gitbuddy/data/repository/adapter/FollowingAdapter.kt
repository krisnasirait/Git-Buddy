package com.krisna.gitbuddy.data.repository.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.krisna.gitbuddy.data.model.response.following.FollowingResponseItem
import com.krisna.gitbuddy.databinding.ItemFollowersFollowingBinding

class FollowingAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val itemList = mutableListOf<FollowingResponseItem?>()

    inner class FollowingViewHolder(private val binding: ItemFollowersFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowingResponseItem) {
            binding.apply {
                tvUsername.text = item.login
                tvUserId.text = "${item.id}"
                Glide.with(root)
                    .load(item.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvProfile)
                root.setOnClickListener{
                    itemClickListener.onFollowingIitemClicked(item.login)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        return FollowingViewHolder(
            ItemFollowersFollowingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFollowing(data: List<FollowingResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onFollowingIitemClicked(username: String)
    }


}