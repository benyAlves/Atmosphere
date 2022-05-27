package com.maluleque.bernardo.atmosphere.api

import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface WeatherApi {
    @GET("group")
    suspend fun fetchCountryWeatherByCityName(
        @Query("id", encoded = true) cities: String,
        @Query("appid") apiId: String = "8ef8cd9e1b203cbc2650c53bbfe18d29",
        @Query("units") units: String = "metric",
        @Query("lang") language: String = Locale.getDefault().language
    ): WeatherListResponse

    @GET("weather")
    suspend fun fetchCountryWeatherByGeoCoordinates(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiId: String = "8ef8cd9e1b203cbc2650c53bbfe18d29",
        @Query("units") units: String = "metric",
        @Query("lang") language: String = Locale.getDefault().language
    ): WeatherDataResponse
}