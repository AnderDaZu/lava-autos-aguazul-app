package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.resposes.PostResponse
import com.anderdadev.lavaautosaguazul.viewHolder.PostViewHolder

class PostAdapter(private val posts: List<PostResponse>): RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return  PostViewHolder(layoutInflater.inflate(R.layout.item_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item =posts[position]
        holder.bind(item)
    }

    override fun getItemCount() = posts.size
}