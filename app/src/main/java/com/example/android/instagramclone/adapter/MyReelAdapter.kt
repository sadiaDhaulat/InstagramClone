package com.example.android.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.android.instagramclone.Models.Post
import com.example.android.instagramclone.Models.Reel
import com.example.android.instagramclone.databinding.MyPostRvDesignBinding
import com.squareup.picasso.Picasso

class MyReelAdapter(var context: Context, var reelList:ArrayList<Reel>, var type: Int) : RecyclerView.Adapter<MyReelAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: MyPostRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=MyPostRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(reelList.get(position).reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.postImage)
    }
}