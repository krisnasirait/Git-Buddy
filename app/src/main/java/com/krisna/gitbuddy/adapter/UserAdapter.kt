package com.krisna.gitbuddy.adapter

import android.annotation.SuppressLint
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.gitbuddy.data.model.response.search.SearchResponseItem
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponseItem
import com.krisna.gitbuddy.databinding.ItemUserRvBinding

class UserAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val itemList = mutableListOf<AllUserResponseItem>()

    inner class UserViewHolder(private val binding: ItemUserRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllUserResponseItem) {
            binding.tvName.text = item.login
            binding.tvId.text = item.nodeId
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.cvProfilePict)
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        itemList[position]?.let { holder.bind(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AllUserResponseItem>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(article: Parcelable)
    }


}
