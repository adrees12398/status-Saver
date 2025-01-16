package com.example.photo.video.StatusDownloader

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun Activity.permissionChecker(array: MutableList<String>, isGrant: (Boolean) -> Unit) {
    Dexter.withActivity(this).withPermissions(array).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
            if (report?.areAllPermissionsGranted() == true) {
                isGrant.invoke(true)
            }
        }

        override fun onPermissionRationaleShouldBeShown(
            permissions: MutableList<PermissionRequest>?,
            token: PermissionToken?
        ) {}

    }).check()
}