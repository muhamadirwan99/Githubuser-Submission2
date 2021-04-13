package com.dicoding.picodiploma.githubusersubmission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubusersubmission2.R
import com.dicoding.picodiploma.githubusersubmission2.model.UserModel
import com.dicoding.picodiploma.githubusersubmission2.databinding.ItemRowFollowingBinding

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val listFollowing = ArrayList<UserModel>()

    fun setData(items: ArrayList<UserModel>) {
        listFollowing.clear()
        listFollowing.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_followers, parent, false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int = listFollowing.size

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowFollowingBinding.bind(itemView)

        fun bind(user: UserModel) {
            Glide.with(itemView.context)
                .load(user.avatarFollowing)
                .apply(RequestOptions()).override(350,550)
                .into(binding.imgItemAvatar)
            binding.tvItemUsername.text = user.usernameFollowing
        }
    }

}