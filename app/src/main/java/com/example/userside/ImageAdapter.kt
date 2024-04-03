package com.example.userside

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userside.databinding.ImageItemBinding


class ImageAdapter(var context: Context, var imageList : ArrayList<String>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    class ViewHolder(var binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(uri: String, context: Context) {
            Glide.with(context)
                .load(uri).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(binding.ivImageShow)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindData(imageList[position],context)
    }
}