package com.example.photo.video.StatusDownloader

import android.annotation.SuppressLint
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
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photo.video.StatusDownloader.adapter.ImagesAdopter
import com.example.photo.video.StatusDownloader.databinding.FragmentImagesActivityBinding
import java.io.File

class ImagesFragment : Fragment() {
    lateinit var sessionManager: SessionManager
    lateinit var binding: FragmentImagesActivityBinding
    private var isPermissionGranted = false
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesActivityBinding.inflate(layoutInflater, container, false)
        sessionManager = SessionManager(requireContext())
        if (sessionManager.getValue()) {
            loadImagesInRecyclerView()
        } else {
            checkWhatsAppPermission()
            permissionfile()
        }
        return binding.root
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                sessionManager.setValue(true)
                loadImagesInRecyclerView()
            }
        }


    private fun permissionfile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:${requireContext().packageName}")
                }
                startActivity(intent)
            } else {
                isPermissionGranted = true
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadImagesInRecyclerView() {
        val businessStatusFiles = getAllWhatsAppStatusFiles()
        if (businessStatusFiles.isNotEmpty()) {
            val adapter = ImagesAdopter(requireContext(), businessStatusFiles){position,click->
                Intent(requireContext(), ImagesActivity::class.java).let { intent ->
                    intent.putExtra("key", businessStatusFiles[position].absolutePath)
                    intent.putExtra("type","Images")
                    startActivity(intent)
                }

            }
            binding.RecyclerViewImage.adapter = adapter
            binding.RecyclerViewImage.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter.notifyDataSetChanged()


        } else {
            Toast.makeText(
                requireContext(),
                "No WhatsApp Business Status found",
                Toast.LENGTH_SHORT
            ).show()
        }
        Toast.makeText(
            requireContext(),
            "Files found: ${businessStatusFiles.size}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkWhatsAppPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                val waStatusUri =
                    Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia%2F.Statuses")
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, waStatusUri)
            }
            activityResultLauncher.launch(intent)
        }
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
                    file.isFile && file.extension in listOf("jpg", "png", "gif")
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
