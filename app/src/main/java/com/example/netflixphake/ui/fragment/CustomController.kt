package com.example.netflixphake.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.netflixphake.R
import com.example.netflixphake.databinding.CustomControllerBinding

class CustomController constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    FrameLayout(context, attrs, defStyleAttr) {
    private var isFullScreen = false
    private var binding: CustomControllerBinding =
        CustomControllerBinding.inflate(LayoutInflater.from(context),this,true)
    init {
        Log.d("xxx",binding.toString())
        binding.btnFullscreen.setOnClickListener{
            if(!isFullScreen){
                binding.btnFullscreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_fullscreen_exit_24))
                (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }else {
                binding.btnFullscreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_fullscreen_24))
                (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

}