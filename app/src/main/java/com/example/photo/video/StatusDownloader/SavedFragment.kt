package com.example.photo.video.StatusDownloader

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photo.video.StatusDownloader.adapter.SavedMediaAdapter
import com.example.photo.video.StatusDownloader.databinding.FragmentSavedBinding
import org.koin.android.ext.android.inject
import java.io.File


class SavedFragment : Fragment() {
    lateinit var binding: FragmentSavedBinding
    lateinit var adapter: SavedMediaAdapter
    private val medialist = mutableListOf<File>()
//    lateinit var viewModel: Viewmodel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(layoutInflater, container, false)

//        viewModel = ViewModelProvider(this)[Viewmodel::class.java]

        recyclerViewSaved()
        (this.activity?.application as AppClass).viewModel.mediaList.observe(viewLifecycleOwner, Observer { media ->
            medialist.clear()
            media.sortedDescending()
            medialist.addAll(media)
            adapter.notifyDataSetChanged()

        })
        (this.activity?.application as AppClass).viewModel.loadMediaFiles()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun recyclerViewSaved() {
        adapter = SavedMediaAdapter(requireContext(), medialist) { position, click -> }
        binding.rVSavedMedia.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rVSavedMedia.adapter = adapter
    }
}