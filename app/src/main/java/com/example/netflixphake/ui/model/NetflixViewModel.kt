package com.example.netflixphake.ui.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.netflixphake.entity.Image
import com.example.netflixphake.entity.NetflixData
import com.example.netflixphake.network.reponsitory.NetflixRepository
import com.example.netflixphake.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NetflixViewModel(application: Application) : BaseViewModel(application) {
    private val nf = NetflixRepository()
    private val nfLiveData: MutableLiveData<List<NetflixData>?> =
        MutableLiveData()

    suspend fun fetchHomeBanner() = coroutineScope.launch {
        val response = nf.getDataFromGithub()
        if (response != null) {
            nfLiveData.postValue(response)
        } else {
            apiErrorReponse.postValue("Can not get Data Netflix")
        }
    }
    fun getData()= nfLiveData
}