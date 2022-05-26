package com.maluleque.bernardo.atmosphere.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val API_URL = "http://api.openweathermap.org/data/2.5/"
private const val CONNECT_TIMEOUT = 10L
private const val READ_TIMEOUT_TIME = 10L

object RetrofitService {

    private val service: WeatherApi by lazy {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_TIME, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(WeatherApi::class.java)
    }


    fun getWeatherApi() = service
}