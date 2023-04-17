package com.example.netflixphake.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netflixphake.R
import com.example.netflixphake.databinding.ImageContainerBinding
import com.example.netflixphake.entity.NetflixData
import com.makeramen.roundedimageview.RoundedImageView

class AdapterUrlImage(private val netflixDataList: List<NetflixData>) : RecyclerView.Adapter<AdapterUrlImage.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = ImageContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return netflixDataList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/" + netflixDataList[position].thumb)
            .into(holder.imageView)
    }

    class ImageViewHolder(customBinding: ImageContainerBinding) : RecyclerView.ViewHolder(customBinding.root) {
        val imageView: RoundedImageView = customBinding.roundImageView
    }
}
