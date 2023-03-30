package com.dicoding.restaurantreview

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//class ReviewAdapter(private val listReview: List<String>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
//        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_review, viewGroup, false))
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.tvItem.text = listReview[position]
//    }
//    override fun getItemCount() = listReview.size
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val tvItem: TextView = view.findViewById(R.id.tvItem)
//    }
//}

class ReviewAdapter(private val listReview: List<User>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_review, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (following, login, followers, photo,id) = listReview[position]
        viewHolder.tvItem.text = login
        Glide.with(viewHolder.itemView.context)
            .load(photo)
            .centerCrop()
            .into(viewHolder.ivItem)
        viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_user", listReview[viewHolder.adapterPosition])
            viewHolder.itemView.context.startActivity(intentDetail)
        }

    }
    override fun getItemCount() = listReview.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
        val ivItem: ImageView = view.findViewById(R.id.ivItem)
    }
}