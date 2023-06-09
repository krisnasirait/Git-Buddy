package com.krisna.gitbuddy.data.repository.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponseItem
import com.krisna.gitbuddy.databinding.ItemUserListBinding

class UserAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val itemList = mutableListOf<AllUserResponseItem>()

    inner class UserViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AllUserResponseItem) {
            binding.apply {
                tvUsername.text = item.login
                Glide.with(root)
                    .load(item.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cvProfile)
                root.setOnClickListener {
                    itemClickListener.onItemUserListClicked(item.login)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserListBinding.inflate(
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
        itemList[position].let { holder.bind(it) }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AllUserResponseItem>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemUserListClicked(username: String)
    }


}
