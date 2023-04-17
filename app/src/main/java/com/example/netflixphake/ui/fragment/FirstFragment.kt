package com.example.netflixphake.ui.fragment

import android.net.Uri
import android.view.*
import android.widget.Toast
import com.example.netflixphake.R
import com.example.netflixphake.databinding.FragmentFirstBinding
import com.example.netflixphake.entity.NetflixData
import com.example.netflixphake.ui.base.BaseFragment
import com.example.netflixphake.ui.model.NetflixViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class FirstFragment : BaseFragment<NetflixViewModel, FragmentFirstBinding>() {
    override fun initActions() {
    }

    override fun initData() {
        if(arguments != null){
            val video = requireArguments().getSerializable("videoinfo",NetflixData::class.java)
            Toast.makeText(context,video?.thumb.toString(),Toast.LENGTH_SHORT).show()
            binding.tvDescription.text = video?.description

            val playerView = binding.player
            val progressBar = binding.progressBar

            val simpleExoPlayer = ExoPlayer.Builder(baseContext)
                .setSeekBackIncrementMs(5000)
                .setSeekForwardIncrementMs(5000)
                .build()
            playerView.player = simpleExoPlayer
            playerView.keepScreenOn = true
            simpleExoPlayer.addListener(object: Player.Listener{
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
    }
}