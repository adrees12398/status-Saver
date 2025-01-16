package com.example.photo.video.StatusDownloader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.StatusDownloader.databinding.ActivityVideoPlaylerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class VideoPlaylerActivity : AppCompatActivity() {
    lateinit var player: SimpleExoPlayer
    lateinit var binding: ActivityVideoPlaylerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlaylerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backVideo.setOnClickListener {
            this.finish()
        }
        mediaController()

    }

    private fun mediaController() {
        val playerView: PlayerView = findViewById(R.id.VideoView)
        val getVideoPath = intent.extras?.getString("Video_path")
        val setVideoUri = Uri.parse(getVideoPath)
        player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player
        val mediaItem = MediaItem.fromUri(setVideoUri)
        player.setMediaItem(mediaItem)

        player.prepare()
        player.playWhenReady = true
   binding.sharecon.setOnClickListener {
       if (!getVideoPath.isNullOrEmpty()) {
           openVideoAnotherApp(getVideoPath)
       }else{
           Toast.makeText(this, "This Video Path is not wrong", Toast.LENGTH_SHORT).show()
           finish()
       }
   }


    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    private fun openVideoInExternalApp(videoPath: String) {
        try {
            // Parse the video path to a URI
            val videoUri = Uri.parse(videoPath)

            // Create an Intent to open the video in an external app
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(videoUri, "video/*") // Specify the MIME type as video
                flags = Intent.FLAG_ACTIVITY_NEW_TASK // Optional: Ensures a new task is started
            }

            // Start the activity to handle the intent
            startActivity(Intent.createChooser(intent, "Open video with"))
        } catch (e: Exception) {
            // Handle any errors that occur
            Toast.makeText(this, "No app found to open this video", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openVideoAnotherApp(VideoPath:String){

        try {
            val videoPath = Uri.parse(VideoPath)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(videoPath,"video/*")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(Intent.createChooser(intent,"Open video with"))
        }catch (e:Exception){
            Toast.makeText(this, "No app found to open this video in your Phone", Toast.LENGTH_SHORT).show()
        }

    }

}