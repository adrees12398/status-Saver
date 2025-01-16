package com.example.photo.video.StatusDownloader.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.photo.video.StatusDownloader.ImagesFragment
import com.example.photo.video.StatusDownloader.SavedFragment
import com.example.photo.video.StatusDownloader.VideoFragment

class Adopter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ImagesFragment()
            }

            1 -> {
                VideoFragment()
            }

            2 -> {
                SavedFragment()
            }

            else -> {
                Fragment()
            }
        }
    }
}