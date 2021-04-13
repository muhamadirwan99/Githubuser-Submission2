package com.dicoding.picodiploma.githubusersubmission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubusersubmission2.R
import com.dicoding.picodiploma.githubusersubmission2.model.UserModel
import com.dicoding.picodiploma.githubusersubmission2.databinding.ItemRowUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder> (){

    private val mData = ArrayList<UserModel>()

    fun setData(items: ArrayList<UserModel>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)

        fun bind(userModel: UserModel) {
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(userModel)
            }

            with(itemView){
                Glide.with(itemView.context)
                    .load(userModel.avatar)
                    .apply(RequestOptions()).override(350,550)
                    .into(binding.imgItemAvatar)

                binding.tvItemUsername.text = userModel.username
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
    }
}