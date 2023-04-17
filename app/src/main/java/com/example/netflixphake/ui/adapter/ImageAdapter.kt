package com.example.netflixphake.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netflixphake.databinding.ImageContainerBinding
import com.example.netflixphake.entity.NetflixData
import com.makeramen.roundedimageview.RoundedImageView


class ImageAdapter(private val images: List<NetflixData>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    var imageSelected: ((position: Int, contact: NetflixData) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = ImageContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(view, this::onVideoSelected)
    }

    private fun onVideoSelected(position: Int){
        imageSelected?.invoke(position,images[position])
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/" + images[position].thumb)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }
    inner class ImageViewHolder(
        customBinding: ImageContainerBinding,
        private val onClick: (Int) -> Unit):
    RecyclerView.ViewHolder(customBinding.root){
        init {
            customBinding.roundImageView.setOnClickListener {
                onClick.invoke(absoluteAdapterPosition)
            }
        }
        val imageView: RoundedImageView = customBinding.roundImageView
    }
}