package com.maluleque.bernardo.atmosphere.repository

import com.maluleque.bernardo.atmosphere.api.*
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class WeatherRepositoryTest{
    private val weatherApi = mockk<WeatherApi>()
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup(){
        weatherRepository = WeatherRepository(weatherApi)
    }
    @Test
    fun fetchCityWeatherByCode() = runBlocking{
        val response = WeatherListResponse(
            count = 2,
            list = listOf()
        )
        coEvery {
            weatherApi.fetchCountryWeatherByCityName(any())
        } returns response

        assertEquals(response, weatherRepository.fetchCityWeatherByCode(listOf()))
    }

    @Test
    fun fetchCityWeatherByCoordinates() = runBlocking {
        val response = WeatherDataResponse(
            id = 1,
            cityName = "maputo",
            wind = WindResponse(
                deg = "ba",
                speed = 2.0
            ),
            main = MainResponse(
                feelsLike = "22",
                humidity = "11",
                max = 23.0,
                min = 17.0,
                pressure = "",
                temp = 9.0
            ),
            weather = listOf()
        )

        coEvery {
            weatherApi.fetchCountryWeatherByGeoCoordinates(any(), any())
        } returns response

        assertEquals(response, weatherRepository.fetchCityWeatherByCoordinates("980", "-89"))
    }
}