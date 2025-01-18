package com.example.photo.video.StatusDownloader

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photo.video.StatusDownloader.adapter.ImagesAdopter
import com.example.photo.video.StatusDownloader.databinding.FragmentImagesActivityBinding
import java.io.File

class ImagesFragment : Fragment() {
    private lateinit var binding: FragmentImagesActivityBinding
    private lateinit var sessionManager: SessionManager

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagesActivityBinding.inflate(layoutInflater, container, false)
        sessionManager = SessionManager(requireContext())

        // Check if the user has granted "Manage All Files" permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            requestManageAllFilesPermission()
        } else if (sessionManager.getWhatsAppBusinessPermission()) {
            loadWhatsAppBusinessStatuses()
        } else {
            requestWhatsAppBusinessFolderAccess()
        }

        return binding.root
    }

    private val manageFilesPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                Toast.makeText(requireContext(), "Manage All Files permission granted", Toast.LENGTH_SHORT).show()
                requestWhatsAppBusinessFolderAccess()
            } else {
                Toast.makeText(requireContext(), "Permission required to proceed", Toast.LENGTH_SHORT).show()
            }
        }

    private fun requestManageAllFilesPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
        manageFilesPermissionLauncher.launch(intent)
    }

    private val folderAccessLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                sessionManager.setWhatsAppBusinessPermission(true)
                loadWhatsAppBusinessStatuses()
            } else {
                Toast.makeText(requireContext(), "Folder access denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun requestWhatsAppBusinessFolderAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                val uri = Uri.parse(
                    "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia%2F.Statuses"
                )
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
            }
            folderAccessLauncher.launch(intent)
        } else {
            Toast.makeText(
                requireContext(),
                "Folder access is not supported on this device.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadWhatsAppBusinessStatuses() {
        val folder = File("/storage/emulated/0/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses")
        if (folder.exists() && folder.isDirectory) {
            val statusFiles = folder.listFiles { file ->
                file.isFile && file.extension in listOf("jpg", "png", "gif", "mp4")
            }?.toList() ?: emptyList()

            if (statusFiles.isNotEmpty()) {
                val adapter = ImagesAdopter(requireContext(), statusFiles) { position, _ ->
                    Intent(requireContext(), ImagesActivity::class.java).apply {
                        putExtra("key", statusFiles[position].absolutePath)
                        putExtra("type", "Images")
                        startActivity(this)
                    }
                }
                binding.RecyclerViewImage.adapter = adapter
                binding.RecyclerViewImage.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "No statuses found in WhatsApp Business.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e("FolderAccess", "WhatsApp Business folder not found or inaccessible.")
            Toast.makeText(requireContext(), "Folder does not exist.", Toast.LENGTH_SHORT).show()
        }
    }
}
