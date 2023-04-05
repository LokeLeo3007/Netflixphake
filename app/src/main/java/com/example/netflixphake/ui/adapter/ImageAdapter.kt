package com.example.netflixphake.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.netflixphake.databinding.ImageContainerBinding
import com.example.netflixphake.entity.Image
import com.makeramen.roundedimageview.RoundedImageView

class ImageAdapter(private val images: List<Image>, viewpager: ViewPager2): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageAdapter.ImageViewHolder {
        val view = ImageContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ImageViewHolder, position: Int) {
        holder.imageView.setImageResource((images[position]).data)
    }

    override fun getItemCount(): Int {
        return images.size
    }
    class ImageViewHolder(private val customBinding: ImageContainerBinding): RecyclerView.ViewHolder(customBinding.root){
        val imageView: RoundedImageView = customBinding.roundImageView
    }
}