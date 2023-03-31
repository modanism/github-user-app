package com.dicoding.restaurantreview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.restaurantreview.databinding.ItemReviewBinding

class FollowAdapter(private val listFollow: List<UserDetailResponse>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserDetailResponse) {

            binding.tvItem.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .centerCrop()
                .into(binding.ivItem)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = listFollow.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFollow[position])
    }
}