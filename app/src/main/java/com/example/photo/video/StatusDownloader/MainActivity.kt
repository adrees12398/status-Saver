package com.example.photo.video.StatusDownloader

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.ViewDebug
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavHost
import com.example.photo.video.StatusDownloader.adapter.Adopter
import com.example.photo.video.StatusDownloader.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.viewpager.adapter = Adopter(this.supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.tab, binding.viewpager) { tab, position ->
            when(position){
                0->{
                    tab.text = "IMAGES"
                }
                1->{
                    tab.text = "VIDEOS"
                }
                2->{
                    tab.text = "SAVED"
                }
            }
        }.attach()
        binding.shimmerLayout.startShimmer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)


        menu?.let {

            for (i in 0 until it.size()) {
                val menuItem = it.getItem(i)


                val iconDrawable = menuItem.icon
                if (iconDrawable != null) {

                    val mutableIcon = iconDrawable.mutate()

                    val color = if (isLightTheme()) {
                        ContextCompat.getColor(this, R.color.white) // Icon color for dark theme
                    } else {
                        ContextCompat.getColor(this, R.color.white) // Icon color for light theme
                    }


                    mutableIcon.setTint(color)


                    menuItem.icon = mutableIcon
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId){
            R.id.RemoveAdds -> {
                startActivity(Intent(this,PremiumActivity::class.java))
            }
             R.id.setting -> startActivity(Intent(this,SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
    private fun isLightTheme(): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}