package com.example.photo.video.StatusDownloader

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.photo.video.StatusDownloader.databinding.ActivityPremiumBinding

class PremiumActivity : AppCompatActivity() {
  lateinit var binding:ActivityPremiumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cross.setOnClickListener {
            this.finish()
        }
    }
}