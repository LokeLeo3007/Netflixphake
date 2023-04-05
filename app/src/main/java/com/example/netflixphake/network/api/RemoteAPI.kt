package com.example.netflixphake.network.api

import com.example.netflixphake.entity.NetflixData
import retrofit2.http.*


interface RemoteAPI {
    @GET("/LokeLeo3007/Netflix/main/data.json")
    suspend fun getDataNetflix(): List<NetflixData>
}
