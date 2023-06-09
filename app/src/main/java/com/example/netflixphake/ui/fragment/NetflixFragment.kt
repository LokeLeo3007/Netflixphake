package com.example.netflixphake.ui.fragment

import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import com.example.netflixphake.R
import com.example.netflixphake.databinding.FragmentNetflixBinding
import com.example.netflixphake.entity.NetflixData
import com.example.netflixphake.ui.adapter.ImageAdapter
import com.example.netflixphake.ui.base.BaseFragment
import com.example.netflixphake.ui.model.NetflixViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class NetflixFragment : BaseFragment<NetflixViewModel, FragmentNetflixBinding>() {
    private lateinit var viewpager: ViewPager2
    var mediaControls: MediaController? = null

    private lateinit var adapter: ImageAdapter
//    private lateinit var adapter: AdapterUrlImage
    private val images: MutableList<NetflixData> = mutableListOf()

    override fun provideLayoutId() = R.layout.fragment_netflix

    override fun provideViewModelClass() = NetflixViewModel::class.java

    private fun loadDataFromServer() {
        lifecycleScope.launch {
            viewModel.fetchHomeBanner()
        }
    }

    override fun initActions() {
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            navigate(R.id.NetflixFragment_to_HomeFragment, null)
        }

        adapter.imageSelected ={_,video ->
            val bundle = Bundle()
            bundle.putSerializable("videoinfo",video)
            navigate(R.id.NetflixFragment_to_FirstFragment,bundle)
        }
    }

    override fun initData() {
        viewModel.getData().observe(
            viewLifecycleOwner
        ){
            result ->
            if (result != null) {
                images.addAll(result)
                val playerView = binding.videotest
                val progressBar = binding.progressBar

                val simpleExoPlayer = ExoPlayer.Builder(baseContext)
                    .setSeekBackIncrementMs(5000)
                    .setSeekForwardIncrementMs(5000)
                    .build()

                playerView.player = simpleExoPlayer
                playerView.keepScreenOn = true

                playerView.useController = false

                (playerView.player as ExoPlayer).addListener(object: Player.Listener{
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        if(playbackState == Player.STATE_BUFFERING){
                            progressBar.visibility = View.VISIBLE
                        } else if (playbackState == Player.STATE_READY){
                            progressBar.visibility = View.GONE
                        }
                    }
                })
                val videoSource = Uri.parse(result[0]?.sources)
                simpleExoPlayer.setMediaItem(MediaItem.fromUri(videoSource))
                simpleExoPlayer.prepare()
                simpleExoPlayer.play()
                adapter.notifyDataSetChanged()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearDisposable()
        binding.videotest.player?.release()
    }

    override fun initView() {
        val handler = Handler(Looper.getMainLooper())
        viewpager = binding.scrollviewpager
        adapter = ImageAdapter(images)
//        adapter = AdapterUrlImage(images)
        viewpager.adapter = adapter

        viewpager.offscreenPageLimit = 3
        viewpager.clipChildren = false
        viewpager.clipToPadding = false

        loadDataFromServer()

        viewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val transfomer = CompositePageTransformer()
        transfomer.addTransformer(MarginPageTransformer(40))

        viewpager.setPageTransformer(transfomer)
        viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val runnable = Runnable{ if(viewpager.currentItem == adapter.itemCount-1) viewpager.currentItem = 0 else viewpager.currentItem = position+1 }
                if (position < (viewpager.adapter?.itemCount ?: 0)) {
                    handler.postDelayed(runnable, timerDelay)
                }

            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == SCROLL_STATE_DRAGGING){
                    handler.removeMessages(0)
                    if(viewpager.currentItem == adapter.itemCount-1) viewpager.currentItem = 0
                }

            }
        })
    }

    companion object {
        private const val timerDelay: Long = 2 * 1000
    }
}