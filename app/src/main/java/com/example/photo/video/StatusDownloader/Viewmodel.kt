package com.example.photo.video.StatusDownloader

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class Viewmodel : ViewModel() {

    private val _mediaList = MutableLiveData<List<File>>()
    val mediaList:LiveData<List<File>> get() = _mediaList
    val commonDirectory = "Media/StatusDownloader"

    fun updateMediaList(newMediaList:List<File>){
        _mediaList.value = newMediaList
    }

    fun loadMediaFiles() {
        val mediaDirectory = File(Environment.getExternalStorageDirectory(),commonDirectory)
        if (mediaDirectory.exists()) {
            _mediaList.value = mediaDirectory.listFiles { file -> file.isFile && (file.extension == "jpg" || file.extension == "mp4") }?.toList() ?: emptyList()
        } else {
            _mediaList.value = emptyList()
        }
    }
}