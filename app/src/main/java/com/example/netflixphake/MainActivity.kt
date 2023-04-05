package com.example.netflixphake

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.netflixphake.databinding.ActivityMainBinding
import com.example.netflixphake.ui.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding?.root)
        val fragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .attach(fragment)
            .commitNow()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}