package com.krisna.gitbuddy.data.repository.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.krisna.gitbuddy.data.entity.FavoriteUser
import com.krisna.gitbuddy.databinding.ItemFollowersFollowingBinding

class FavoriteAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val itemList = mutableListOf<FavoriteUser?>()

    inner class FavoriteViewHolder(private val binding: ItemFollowersFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteUser) {
            binding.apply {
                tvUsername.text = item.username
                tvUserId.text = "${item.id}"
                Glide.with(root)
                    .load(item.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvProfile)
                root.setOnClickListener{
                    itemClickListener.onFavoriteItemClicked(item.username!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemFollowersFollowingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        itemList[position]?.let {
            holder.bind(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFavorite(data: List<FavoriteUser?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onFavoriteItemClicked(username: String)
    }


}