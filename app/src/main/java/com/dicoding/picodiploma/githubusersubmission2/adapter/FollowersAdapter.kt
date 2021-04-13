package com.dicoding.picodiploma.githubusersubmission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubusersubmission2.model.FollowersModel
import com.dicoding.picodiploma.githubusersubmission2.R
import com.dicoding.picodiploma.githubusersubmission2.databinding.ItemRowFollowersBinding

class FollowersAdapter: RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private val listFollowers = ArrayList<FollowersModel>()

    fun setData(items: ArrayList<FollowersModel>) {
        listFollowers.clear()
        listFollowers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_followers, parent, false)
        return FollowersViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    override fun getItemCount(): Int = listFollowers.size

    inner class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowFollowersBinding.bind(itemView)

        fun bind(user: FollowersModel) {
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions()).override(350,550)
                .into(binding.imgItemAvatar)
            binding.tvItemUsername.text = user.username
        }
    }

}