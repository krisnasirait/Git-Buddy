package com.krisna.gitbuddy.data.repository.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.krisna.gitbuddy.R
import com.krisna.gitbuddy.data.entity.FavoriteUser
import com.krisna.gitbuddy.data.model.response.alluser.AllUserResponseItem
import com.krisna.gitbuddy.databinding.ItemUserListBinding
import com.krisna.gitbuddy.presentation.viewmodel.GithubViewModel

class UserAdapter(
    private val itemClickListener: OnItemClickListener,
    private val viewModel: GithubViewModel
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val itemList = mutableListOf<AllUserResponseItem>()

    inner class UserViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
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
                if (item.isFavorite) {
                    btnFavorite.setImageDrawable(
                        AppCompatResources.getDrawable(
                            root.context,
                            R.drawable.ic_baseline_favorite_24
                        )
                    )
                } else {
                    btnFavorite.setImageDrawable(
                        AppCompatResources.getDrawable(
                            root.context,
                            R.drawable.ic_favorite_border_24
                        )
                    )
                }
                binding.btnFavorite.setOnClickListener {
                    if (item.isFavorite) {
                        viewModel.deleteFavoriteUser(FavoriteUser(item.id, item.login, item.avatarUrl))
                        item.isFavorite = false
                        btnFavorite.setImageDrawable(
                            AppCompatResources.getDrawable(
                                root.context,
                                R.drawable.ic_favorite_border_24
                            )
                        )
                    } else {
                        viewModel.insertFavoriteUser(FavoriteUser(item.id, item.login, item.avatarUrl))
                        item.isFavorite = true
                        btnFavorite.setImageDrawable(
                            AppCompatResources.getDrawable(
                                root.context,
                                R.drawable.ic_baseline_favorite_24
                            )
                        )
                    }
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
