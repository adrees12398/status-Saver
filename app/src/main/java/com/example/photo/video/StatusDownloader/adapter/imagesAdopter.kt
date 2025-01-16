package com.example.photo.video.StatusDownloader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photo.video.StatusDownloader.R
import java.io.File

class ImagesAdopter(private val context: Context, private val files: List<File>, val click: (Int,String) -> Unit) :
    RecyclerView.Adapter<ImagesAdopter.ImageViewHolder>() {

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val file = files[position]
        Glide.with(context)
            .load(file)
            .into(holder.imageView)
        holder.imageView.setOnClickListener {
            click.invoke(position,"Image")
        }
    }

    override fun getItemCount(): Int = files.size
}