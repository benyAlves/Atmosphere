package com.maluleque.bernardo.atmosphere.repository

import com.maluleque.bernardo.atmosphere.api.WeatherApi
import com.maluleque.bernardo.atmosphere.api.WeatherData


class WeatherRepository(private val weatherApi: WeatherApi) {

    suspend fun fetchCityWeatherByCode(
        cityCodes: List<Int>
    ) = weatherApi.fetchCountryWeatherByCityName(
            cities = cityCodes.joinToString()
    )


    suspend fun fetchCityWeatherByCoordinates(
        latitude: String,
        longitude: String
    ): WeatherData {
        return weatherApi.fetchCountryWeatherByGeoCoordinates(latitude, longitude)
    }
}