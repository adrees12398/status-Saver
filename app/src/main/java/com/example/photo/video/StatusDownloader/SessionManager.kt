package com.example.photo.video.StatusDownloader

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SessionManager(var context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences("Share_File", Context.MODE_PRIVATE)


    companion object {
        private const val KEY_IS_WHATSAPP_SELECTED = "isWhatsAppSelected"
        private const val KEY_WHATSAPP_PERMISSION = "whatsappPermission"
        private const val KEY_WHATSAPP_BUSINESS_PERMISSION = "whatsappBusinessPermission"
    }

    fun getTheme(): Boolean {
        return pref.getBoolean("theme", false)
    }

    fun setTheme(ischecked: Boolean) {
        pref.edit().putBoolean("theme", ischecked).apply()
    }
//
//    fun getWhatsAppPermission(): Boolean {
//        return pref.getBoolean("isGrant", false)
//    }
//
//    fun setWhatsAppPermission(value: Boolean) {
//        pref.edit().putBoolean("isGrant", value).apply()
//    }
//
//    @SuppressLint("CommitPrefEdits")
//    fun setWhatsAppBusinessPermission(value: Boolean){
//        pref.edit().putBoolean("isGrant",value)
//    }
//
//    fun getWhatsAppBusinessPermission(): Boolean {
//        return pref.getBoolean("isGrant", false)
//    }

    fun setVideo(value: Boolean) {
        pref.edit().putBoolean("isGrant", value).apply()
    }


    // Determines if WhatsApp is selected (default is true)
    fun isWhatsAppSelected(): Boolean {
        return pref.getBoolean(KEY_IS_WHATSAPP_SELECTED, true)
    }

    // Sets the user's preference for WhatsApp or WhatsApp Business
    fun setWhatsAppSelected(isWhatsApp: Boolean) {
        pref.edit().putBoolean(KEY_IS_WHATSAPP_SELECTED, isWhatsApp).apply()
    }

    // Retrieves WhatsApp permission state
    fun getWhatsAppPermission(): Boolean {
        return pref.getBoolean(KEY_WHATSAPP_PERMISSION, false)
    }

    // Sets WhatsApp permission state
    fun setWhatsAppPermission(isGranted: Boolean) {
        pref.edit().putBoolean(KEY_WHATSAPP_PERMISSION, isGranted).apply()
    }

    // Retrieves WhatsApp Business permission state
    fun getWhatsAppBusinessPermission(): Boolean {
        return pref.getBoolean(KEY_WHATSAPP_BUSINESS_PERMISSION, false)
    }

    // Sets WhatsApp Business permission state
    fun setWhatsAppBusinessPermission(isGranted: Boolean) {
        pref.edit().putBoolean(KEY_WHATSAPP_BUSINESS_PERMISSION, isGranted).apply()
    }


}