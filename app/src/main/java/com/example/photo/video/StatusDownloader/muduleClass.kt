package com.example.photo.video.StatusDownloader

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { Viewmodel() }
}