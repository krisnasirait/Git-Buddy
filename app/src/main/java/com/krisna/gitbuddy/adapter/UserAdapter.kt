package com.krisna.gitbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.gitbuddy.data.model.response.UserResponse
import com.krisna.gitbuddy.databinding.ItemUserRvBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val itemUserResponse = mutableListOf<UserResponse?>()

    inner class UserViewHolder(private val binding: ItemUserRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserResponse) {
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
    fun setDataUser(userResponse: List<UserResponse?>) {
        itemUserResponse.clear()
        itemUserResponse.addAll(userResponse)
        notifyDataSetChanged()
    }


}