package com.krisna.gitbuddy.adapter

import android.annotation.SuppressLint
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.gitbuddy.data.model.response.search.SearchResponseItem
import com.krisna.gitbuddy.databinding.ItemSearchBinding

class SearchAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val itemList = mutableListOf<SearchResponseItem?>()

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchResponseItem) {
            binding.tvName.text = item.login
            binding.tvId.text = item.id.toString()
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.cvProfilePict)
            binding.root.setOnClickListener{
                itemClickListener.onSearchItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSearch(data: List<SearchResponseItem?>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onSearchItemClicked(searchResponseItem: Parcelable)
    }


}