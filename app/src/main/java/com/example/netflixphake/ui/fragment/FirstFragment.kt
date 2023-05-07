package com.example.netflixphake.ui.fragment

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.netflixphake.R
import com.example.netflixphake.databinding.FragmentFirstBinding
import com.example.netflixphake.entity.NetflixData
import com.example.netflixphake.ui.base.BaseFragment
import com.example.netflixphake.ui.`interface`.OnEnterFullScreenListener
import com.example.netflixphake.ui.model.NetflixViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class FirstFragment : BaseFragment<NetflixViewModel, FragmentFirstBinding>(), OnEnterFullScreenListener{
    private var isFullScreen = false
    override fun initActions() {
        val fullScreen = binding.player.findViewById<ImageView>(R.id.exo_fullscreen)
        fullScreen.setOnClickListener {
            if(!isFullScreen) {
                fullScreen.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.baseline_fullscreen_exit_24))
                (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            } else {
                fullScreen.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.baseline_fullscreen_24))
                (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
            isFullScreen = !isFullScreen
        }
    }

    override fun initData() {
        if(arguments != null){
            val playerView = binding.player
            val progressBar = binding.progressBar

            val simpleExoPlayer = ExoPlayer.Builder(baseContext).build()

            playerView.player = simpleExoPlayer
            playerView.keepScreenOn = true

            val video = requireArguments().getSerializable("videoinfo",NetflixData::class.java)
            Toast.makeText(context,video?.thumb.toString(),Toast.LENGTH_SHORT).show()
            binding.tvDescription.text = video?.description

            playerView.useController = true
            (playerView.player as ExoPlayer).addListener(object: Player.Listener{
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if(playbackState == Player.STATE_BUFFERING){
                        progressBar.visibility = View.VISIBLE
                    } else if (playbackState == Player.STATE_READY){
                        progressBar.visibility = View.GONE
                    }
                }
            })
            val videoSource = Uri.parse(video?.sources)
            simpleExoPlayer.setMediaItem(MediaItem.fromUri(videoSource))
            simpleExoPlayer.prepare()
            simpleExoPlayer.play()
        }
    }

    override fun initView() {
    }

    override fun provideLayoutId() = R.layout.fragment_first

    override fun provideViewModelClass() = NetflixViewModel::class.java

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearDisposable()
        binding.player.player?.release()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun enterFullScreenMode(isFullScreen: Boolean) {
        Log.d("FullScreen", "Is fullscreen: $isFullScreen")
    }
}