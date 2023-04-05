package com.example.netflixphake.ui.fragment

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import com.example.netflixphake.R
import com.example.netflixphake.databinding.FragmentNetflixBinding
import com.example.netflixphake.entity.Image
import com.example.netflixphake.ui.adapter.ImageAdapter
import com.example.netflixphake.ui.base.BaseFragment
import com.example.netflixphake.ui.model.NetflixViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class NetflixFragment : BaseFragment<NetflixViewModel, FragmentNetflixBinding>() {
    private lateinit var viewpager: ViewPager2

    private lateinit var adapter: ImageAdapter
    private val images: MutableList<Image> = mutableListOf()

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
    }

    override fun initData() {
        viewModel.getData().observe(
            viewLifecycleOwner
        ){
            result ->
            Log.d("xxx",result?.size.toString())
        }
    }

    override fun initView() {

        val handler = Handler(Looper.getMainLooper())
        viewpager = binding.scrollviewpager
        images.add(Image(R.drawable._1))
        images.add(Image(R.drawable._2))
        images.add(Image(R.drawable._3))
        images.add(Image(R.drawable._4))
        adapter = ImageAdapter(images,viewpager)
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