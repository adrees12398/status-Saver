package com.example.photo.video.StatusDownloader

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photo.video.StatusDownloader.adapter.ImagesAdopter
import com.example.photo.video.StatusDownloader.databinding.FragmentVideoBinding
import java.io.File


class VideoFragment : Fragment() {
    lateinit var binding: FragmentVideoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(layoutInflater, container, false)
        loadImagesRecyclerView()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadImagesRecyclerView() {
        val statusVideos = getAllWhatsAppStatusFiles()
        if (statusVideos.isNotEmpty()) {

            val adapter = ImagesAdopter(requireContext(), statusVideos) { position, clcik ->
                Intent(requireContext(), ImagesActivity::class.java).let { intent ->
                    intent.putExtra("key", statusVideos[position].absolutePath)
                    intent.putExtra("type", "Videos")
                    startActivity(intent)
                }
            }
            binding.RecyclerViewVideo.adapter = adapter
            binding.RecyclerViewVideo.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(
                requireContext(),
                "No WhatsappBusinessVideoStatusFound",
                Toast.LENGTH_SHORT
            ).show()
        }
        Toast.makeText(requireContext(), "${statusVideos.size}", Toast.LENGTH_SHORT).show()

    }

    private fun getAllWhatsAppStatusFiles(): List<File> {
        val statusFolders = listOf(
            File("/storage/emulated/0/Android/media/com.whatsapp/WhatsApp/Media/.Statuses"), // WhatsApp
            File("/storage/emulated/0/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses") // WhatsApp Business
        )

        val statusFiles = mutableListOf<File>()
        for (folder in statusFolders) {
            if (folder.exists() && folder.isDirectory) {
                val files = folder.listFiles { file ->
                    file.isFile && file.extension in listOf("mp4", "mkv", "avi", "mov", "flv")
                }
                if (files != null) {
                    statusFiles.addAll(files)
                }
            } else {
                Log.e("StatusFolder", "Folder does not exist or is not a directory: ${folder.absolutePath}")
            }
        }

        return statusFiles
    }
}