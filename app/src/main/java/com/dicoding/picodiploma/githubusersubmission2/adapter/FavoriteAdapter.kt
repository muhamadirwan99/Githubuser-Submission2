package com.dicoding.picodiploma.githubusersubmission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubusersubmission2.R
import com.dicoding.picodiploma.githubusersubmission2.databinding.ItemRowFavoriteBinding
import com.dicoding.picodiploma.githubusersubmission2.model.FavoriteModel



class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorite = ArrayList<FavoriteModel>()

    fun setData(items: ArrayList<FavoriteModel>) {
        listFavorite.clear()
        listFavorite.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_favorite, parent, false)
        return FavoriteViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowFavoriteBinding.bind(itemView)

        fun bind(user: FavoriteModel) {
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions()).override(350,550)
                .into(binding.imgItemAvatar)
            binding.tvItemUsername.text = user.username
        }
    }

}