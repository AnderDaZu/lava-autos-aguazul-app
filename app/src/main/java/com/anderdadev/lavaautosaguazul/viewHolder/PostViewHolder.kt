package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemPostBinding
import com.anderdadev.lavaautosaguazul.resposes.PostResponse
import com.squareup.picasso.Picasso

class PostViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var mBinding = ItemPostBinding.bind(view)

    fun bind(post: PostResponse){
        Picasso.get().load(post.url).into(mBinding.ivPost)
        mBinding.tvType.text = post.type
        mBinding.tvTitle.text = post.title
        mBinding.tvExtract.text = post.extract
    }
}