package com.example.photo.video.StatusDownloader.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photo.video.StatusDownloader.ImagesActivity
import com.example.photo.video.StatusDownloader.R
import com.example.photo.video.StatusDownloader.VideoPlaylerActivity
import java.io.File

class SavedMediaAdapter(val context: Context, private var mediaList:List<File>,val click:(Int,String) -> Unit):RecyclerView.Adapter<SavedMediaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMediaViewHolder {
      val inflate = LayoutInflater.from(context).
      inflate(R.layout.itemvideoitemsaved,parent,false)
        return SavedMediaViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    override fun onBindViewHolder(holder: SavedMediaViewHolder, position: Int) {
        val file = mediaList[position]
        if (file.extension in listOf("jpg","png","gif","bmp")){
            Glide.with(context).load(file).into(holder.imageView)
            holder.VideoIcon.visibility = View.GONE
            val intent = Intent()
        }else if (file.extension in listOf("mp4","mkv", "avi", "mov", "flv")){
            Glide.with(context).load(file).into(holder.imageView)
            holder.VideoIcon.visibility = View.VISIBLE
            holder.imageView.setOnClickListener {
               val  intent = Intent(context,VideoPlaylerActivity::class.java).apply {
                   putExtra("Video_path",file.absolutePath)
               }
                context.startActivity(intent)
            }
        }
    }
}

class SavedMediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val VideoIcon: ImageView = itemView.findViewById(R.id.videoIcon)
}