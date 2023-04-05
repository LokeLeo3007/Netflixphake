package com.example.netflixphake.network.reponsitory

abstract class BaseRepository<T> {

    protected var apiService: T? = null


    init {
        apiService = createApiService()
    }

    protected abstract fun createApiService(): T?
}