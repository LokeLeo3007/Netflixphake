package com.example.netflixphake.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.netflixphake.R
import com.example.netflixphake.databinding.CustomControllerBinding
import com.example.netflixphake.ui.`interface`.OnEnterFullScreenListener


class CustomController @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var binding: CustomControllerBinding? = null
    private var isFullScreen = false
    var listener: OnEnterFullScreenListener? = null

    var getsFullScreen: ((isFullScreen: Boolean)-> Unit)? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = CustomControllerBinding.inflate(inflater)
        Log.d("xxx","xxx")
    }
    init {
        val fullScreen = binding?.exoFullscreen
        fullScreen?.setOnClickListener() {

        }
    }
}


//class CustomController(
//    context: Context,
//    private val playerView: PlayerView,
//    progressBar: ProgressBar,
//    private var isFullScreen: Boolean
//) : FrameLayout(context) {
//    private var binding: CustomControllerBinding =
//        CustomControllerBinding.inflate(LayoutInflater.from(context),this,true)
//    init {
//        val timeBar = binding.exoProgress
//
//        binding.exoFullscreen.setOnClickListener {
//            if(!isFullScreen) {
//                binding.exoFullscreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_fullscreen_exit_24))
//                (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//
//            } else {
//                binding.exoFullscreen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_fullscreen_24))
//                (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//            }
//            isFullScreen = !isFullScreen
//        }
//        binding.exoRew.setOnClickListener {
//            playerView.player?.currentPosition?.minus(5000)
//                ?.let { it1 -> playerView.player?.seekTo(it1) }
//        }
//        binding.exoFfwd.setOnClickListener {
//            playerView.player?.currentPosition?.plus(5000)
//                ?.let { it1 -> playerView.player?.seekTo(it1) }
//        }
//
//        binding.exoPause.setOnClickListener {
//            if(playerView.player?.playWhenReady == true){
//                binding.exoPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_play_arrow_24))
//                playerView.player?.pause()
//            } else {
//                binding.exoPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_pause_24))
//                playerView.player?.play()
//            }
//        }
//
//        timeBar.addListener(object : TimeBar.OnScrubListener{
//            override fun onScrubStart(timeBar: TimeBar, position: Long) {
//                playerView.player?.playWhenReady = false
//                timeBar.setPosition(position)
//                updateTimeBar()
//            }
//
//            override fun onScrubMove(timeBar: TimeBar, position: Long) {
//                playerView.player?.seekTo(position)
//                updateTimeBar()
//            }
//
//            override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
//                playerView.player?.playWhenReady = true
//                updateTimeBar()
//            }
//
//        })
//
//        playerView.player?.addListener(object : Player.Listener {
//            override fun onPlaybackStateChanged(state: Int) {
//                if (state == Player.STATE_ENDED) {
//                    playerView.player!!.seekTo(0)
//                    playerView.player!!.playWhenReady = false
//                    updateProgress()
//                }
//            }
//
//            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                if(playbackState == Player.STATE_BUFFERING){
//                    progressBar.visibility = View.VISIBLE
//                } else if (playbackState == Player.STATE_READY){
//                    progressBar.visibility = View.GONE
//                }
//            }
//
//            override fun onIsPlayingChanged(isPlaying: Boolean) {
//                updateProgress()
//            }
//        })
//
//    }
//    private fun updateProgress() {
//        val currentMs = playerView.player?.currentPosition
//        val durationMs = playerView.player?.duration
//        if (durationMs != null) {
//            if (durationMs > 0) {
//                binding.exoPosition.text = currentMs?.let { formatTime(it) }
//                binding.exoDuration.text = formatTime(durationMs)
//            }
//        }
//        postDelayed({ updateProgress() }, 1000)
//    }
//
//    private fun updateTimeBar() {
//        val position = playerView.player?.currentPosition ?: 0L
//        val duration = playerView.player?.duration ?: 0L
//        playerView.player?.seekTo(position)
//        postDelayed({ updateTimeBar() }, 1000)
//    }
//
//    private fun formatTime(timeMs: Long): String {
//        val seconds = (timeMs / 1000).toInt() % 60
//        val minutes = (timeMs / (1000 * 60)).toInt() % 60
//        val hours = (timeMs / (1000 * 60 * 60)).toInt()
//
//        return if (hours > 0) {
//            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
//        } else {
//            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
//        }
//    }
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//    }
//}