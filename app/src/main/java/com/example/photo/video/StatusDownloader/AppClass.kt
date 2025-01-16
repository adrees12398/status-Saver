package com.example.photo.video.StatusDownloader

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

 open class AppClass() : Application() {

    val viewModel: Viewmodel by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppClass.applicationContext)
            printLogger(Level.DEBUG)
            modules(appModule)
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}