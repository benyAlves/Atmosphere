package com.maluleque.bernardo.atmosphere.repository

import com.maluleque.bernardo.atmosphere.api.WeatherApi
import com.maluleque.bernardo.atmosphere.api.WeatherDataResponse
import com.maluleque.bernardo.atmosphere.view.model.City


class WeatherRepository(private val weatherApi: WeatherApi) {

    suspend fun fetchCityWeatherByCode(
        cities: List<City>
    ) = weatherApi.fetchCountryWeatherByCityName(
        //TODO remove later
            cities = "1040652,2267057,4865871,2968815,4348460,2618425,6127261,3846616,4192205,4548393,4739232"
    )


    suspend fun fetchCityWeatherByCoordinates(
        latitude: String,
        longitude: String
    ): WeatherDataResponse {
        return weatherApi.fetchCountryWeatherByGeoCoordinates(latitude, longitude)
    }
}