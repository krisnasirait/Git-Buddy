package com.krisna.gitbuddy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krisna.gitbuddy.data.model.User
import com.krisna.gitbuddy.databinding.ItemUserRvBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val itemUser = mutableListOf<User?>()

    inner class UserViewHolder(private val binding: ItemUserRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
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
        return itemUser.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        itemUser[position]?.let { holder.bind(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataUser(user: List<User?>) {
        itemUser.clear()
        itemUser.addAll(user)
        notifyDataSetChanged()
    }


}