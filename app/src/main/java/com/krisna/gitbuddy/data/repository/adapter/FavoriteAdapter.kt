package com.krisna.gitbuddy.data.repository.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.krisna.gitbuddy.data.entity.FavoriteUser
import com.krisna.gitbuddy.databinding.ItemUserFavoriteBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class FavoriteAdapter(
    private val itemClickListener: OnItemClickListener,
    private val viewModel: GithubViewModel
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val itemList = mutableListOf<FavoriteUser?>()

    inner class FavoriteViewHolder(private val binding: ItemUserFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var itemId: Int? = null
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
                btnDelete.setOnClickListener {
                    itemId?.let { id ->
                        val position = itemList.indexOfFirst { it?.id == id }
                        if (position != -1) {
                            viewModel.deleteFavoriteUser(item)
                            removedItem(position)
                        }
                    }
                }
                itemId = item.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemUserFavoriteBinding.inflate(
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

    fun removedItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
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