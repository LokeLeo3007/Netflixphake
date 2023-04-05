package com.example.netflixphake.network

import com.example.netflixphake.network.api.RemoteAPI
import com.example.netflixphake.network.provider.RetrofitService


class APIHandlerBuilder {
    companion object {
        private var clientAPIService: RemoteAPI? = null

        @Synchronized
        fun getApiService(url: String): RemoteAPI? {
            if (clientAPIService == null) {
                clientAPIService = initApiService(url)
            }
            return clientAPIService
        }

        private fun initApiService(url: String): RemoteAPI {
            return RetrofitService.create(RemoteAPI::class.java, url)
        }
    }
}

