package com.example.photo.video.StatusDownloader

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.photo.video.StatusDownloader.databinding.ActivityImagesBinding
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Locale

@Suppress("IMPLICIT_CAST_TO_ANY")
class ImagesActivity : AppCompatActivity() {

    private var getImage: String? = null
    private var getVideo: String? = null
    lateinit var binding: ActivityImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.downloadbtn.setOnClickListener {
            if (getImage != null && File(getImage).exists()) {
                saveMediaToGallery(File(getImage))
            } else {
                Toast.makeText(this, "Invalid file path", Toast.LENGTH_SHORT).show()
            }
        }
        binding.sharebtn.setOnClickListener {
            if (!getImage.isNullOrEmpty()) {
                shareMedia(getImage!!)
            } else if (!getVideo.isNullOrEmpty()) {
                shareMedia(getVideo!!)
            }
        }
        getImage()
        getVideo()

        binding.backPress.setOnClickListener {
            this.finish()
        }

        binding.shimmar.startShimmer()
    }

    fun getImage() {
        getImage = intent.extras?.getString("key", "")
        Glide.with(this).load(getImage).into(binding.showImage)
        val settext = intent.extras?.getString("type")
        binding.textHeading.text = settext.toString()
    }

    fun getVideo() {
        getVideo = intent.extras?.getString("key", "")
        Glide.with(this).load(getVideo).into(binding.showImage)
        val setTextVideo = intent.extras?.getString("type")
        binding.textHeading.text = setTextVideo.toString()
    }

    private fun saveMediaToGallery(mediaFile: File) {
        try {
            val fileName = mediaFile.name
            val fileExtension = mediaFile.extension.toLowerCase(Locale.ROOT)
            val mimeType: String
            val uri: Uri
            val fos: OutputStream?

            val commonDirectory = "Media/StatusDownloader"

            if (fileExtension in listOf("jpg", "jpeg", "png", "gif", "bmp")) {
                mimeType = "image/$fileExtension"
            } else if (fileExtension in listOf("mp4", "mkv", "avi", "mov", "flv")) {
                mimeType = "video/$fileExtension"
            } else {
                throw IllegalArgumentException("Unsupported file type")
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentResolver = applicationContext.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, commonDirectory)
                }
                uri = if (mimeType.startsWith("image")) {
                    contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )!!
                } else {
                    contentResolver.insert(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )!!
                }
                fos = contentResolver.openOutputStream(uri)
            } else {
                val directory = File(getExternalFilesDir(null)?.absolutePath + "/$commonDirectory")
                if (!directory.exists()) directory.mkdirs()
                val savedFile = File(directory, fileName)
                fos = FileOutputStream(savedFile)
            }

            // Copy media file content to the output stream
            fos?.use { outputStream ->
                mediaFile.inputStream().use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            (application as AppClass).viewModel.loadMediaFiles()

            Toast.makeText(this, "saved to gallery!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save media: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun shareMedia(VideoPath: String) {
        try {
            val videopath = Uri.parse(VideoPath)
            val fileextenstion = VideoPath.substringAfterLast(".")
            val mimeType = if (fileextenstion in listOf("mp4", "mkv", "avi", "mov", "flv") ){
               "video/*"
            }else {
                "images/*"
            }
            val share = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM,videopath)
            }

            startActivity(Intent.createChooser(share, "Share_via"))
        } catch (e: Exception) {
            Toast.makeText(this, "No app to find to share", Toast.LENGTH_SHORT).show()
        }
    }
}