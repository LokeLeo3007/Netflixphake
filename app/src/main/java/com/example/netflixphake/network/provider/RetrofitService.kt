package com.example.netflixphake.network.provider

import com.example.netflixphake.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitService {

    companion object {


        /**
         * Valid instance of [okhttp3.OkHttpClient] for reuse across
         * retrofit instances.
         */
        private val httpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        /**
         * Valid instance of [retrofit2.Retrofit.Builder] for reuse across
         * retrofit instances.
         *
         */
        private val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())

        /**
         * Create an implementation of the API endpoints defined by the `service` interface.
         *
         * @param service      valid retrofit service definition
         * @param baseUrl      valid service base url
         * @return an object of type S from the `service` creation
         */
        fun <S> create(
            service: Class<S>?,
            baseUrl: String,
        ): S {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpClient.addInterceptor(
                    httpLoggingInterceptor.apply {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }

            val client = httpClient.build()
            val retrofit = retrofitBuilder
                .baseUrl(baseUrl)
                .client(client)
                .build()
            return retrofit.create(service)
        }
    }
}