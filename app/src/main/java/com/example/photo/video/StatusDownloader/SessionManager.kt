package com.example.photo.video.StatusDownloader

import android.content.Context
import android.content.SharedPreferences

class SessionManager(var context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences("Share_File", Context.MODE_PRIVATE)

    fun getTheme(): Boolean {
        return pref.getBoolean("theme", false)
    }

    fun setTheme(ischecked: Boolean) {
        pref.edit().putBoolean("theme", ischecked).apply()
    }

    fun getValue(): Boolean {
        return pref.getBoolean("isGrant", false)
    }

    fun setValue(value: Boolean) {
        pref.edit().putBoolean("isGrant", value).apply()
    }

    fun getVideo(): Boolean {
        return pref.getBoolean("isGrant", false)
    }

    fun setVideo(value: Boolean) {
        pref.edit().putBoolean("isGrant", value).apply()
    }
}