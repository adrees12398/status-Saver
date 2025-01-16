package com.example.photo.video.StatusDownloader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.StatusDownloader.databinding.ActivityCustomerSupportBinding

class CustomerSupportActivity : AppCompatActivity() {
    lateinit var binding:ActivityCustomerSupportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backspace.setOnClickListener {
            this.finish()
        }
    }
}