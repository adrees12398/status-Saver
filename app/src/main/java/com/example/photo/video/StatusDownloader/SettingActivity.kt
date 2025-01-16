package com.example.photo.video.StatusDownloader

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.photo.video.StatusDownloader.databinding.ActivitySettingBinding
import com.google.android.material.button.MaterialButton

class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)

        binding.backPress.setOnClickListener {
            this.finish()
        }

        binding.adds.setOnClickListener {
            startActivity(Intent(this, PremiumActivity::class.java))
        }
        binding.themeView.setOnClickListener {
            themeDialog()
        }
        binding.privacyView.setOnClickListener {
            val uri = "https://zipoapps.com/statussavermax/terms/"
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(uri)
                startActivity(this)
            }
        }
        binding.ViewTerm.setOnClickListener {
            val uri = "https://zipoapps.com/statussavermax/terms/"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(uri)
            }
            startActivity(intent)
        }
        binding.shareView.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND).apply {
                setType("text/plain")
                putExtra(Intent.EXTRA_TEXT, "check out this awesome app: $`package`")

            }
            startActivity(Intent.createChooser(share, "Open with"))
        }
        binding.rateView.setOnClickListener {
            alertdailog()
            Toast.makeText(this, "ShareView", Toast.LENGTH_SHORT).show()
        }
        binding.customerView.setOnClickListener {
            startActivity(Intent(this, CustomerSupportActivity::class.java))
        }
    }

    private fun themeDialog() {
        val dialog = AlertDialog.Builder(this)
        val inflate = layoutInflater
        val view = inflate.inflate(R.layout.alertdialogtheme, null)
        dialog.setView(view)
        val dafaultbutton = view.findViewById<RadioButton>(R.id.systemDefault)
        val lightbutton = view.findViewById<RadioButton>(R.id.Light)
        val darkbutton = view.findViewById<RadioButton>(R.id.Dark)
        val materialbutton = view.findViewById<MaterialButton>(R.id.cancel)
        val alertdialog = dialog.create()


        sessionManager.getTheme().let {
            if (it){
                darkbutton.isChecked = true
            }else{
                lightbutton.isChecked = true
            }
        }


        lightbutton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sessionManager.setTheme(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                alertdialog.dismiss()
            }
        }

        darkbutton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sessionManager.setTheme(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                alertdialog.dismiss()
            }

        }

        materialbutton.setOnClickListener {
            alertdialog.dismiss()
        }

        alertdialog.show()
    }

    private fun alertdailog() {
        val dailog = AlertDialog.Builder(this, R.style.TransparentAlertDialog)
        val inflate = layoutInflater
        val View = inflate.inflate(R.layout.ratingview, null)
        dailog.setView(View)
        val alertDialog = dailog.create()
        val cross = View.findViewById<ImageView>(R.id.close)
        val ratingBar = View.findViewById<RatingBar>(R.id.rating)
        val button = View.findViewById<MaterialButton>(R.id.MaterialButton)
        ratingBar.rating = 2.5f
        ratingBar.stepSize = .5f
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, b ->
            Toast.makeText(this, "rating: $rating", Toast.LENGTH_SHORT).show()
        }
        cross.setOnClickListener {
            alertDialog.dismiss()
        }
        button.setOnClickListener {
            startActivity(Intent(this, CustomerSupportActivity::class.java))
        }

        alertDialog.show()
    }
}