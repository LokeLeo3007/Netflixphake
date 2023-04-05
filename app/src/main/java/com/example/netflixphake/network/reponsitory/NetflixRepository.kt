package com.example.netflixphake.network.reponsitory

import com.example.netflixphake.network.APIHandlerBuilder
import com.example.netflixphake.network.api.RemoteAPI

class NetflixRepository: BaseRepository<RemoteAPI>() {
    suspend fun getDataFromGithub() = apiService?.getDataNetflix()

    override fun createApiService(): RemoteAPI? =
        APIHandlerBuilder.getApiService("https://raw.githubusercontent.com/")
}