package com.maluleque.bernardo.atmosphere.view.model

import android.text.format.DateFormat
import com.maluleque.bernardo.atmosphere.api.*
import com.squareup.moshi.JsonClass
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class WeatherInfo {

    class WeatherResponseInfo(
        val count: Int,
        val list: List<WeatherData>
    ){
        constructor(weatherListResponse: WeatherListResponse) : this (
                    count = weatherListResponse.count,
                    list = weatherListResponse.list.map(::WeatherData)
                )
    }


    data class WeatherData(
        val id: Int,
        val cityName: String,
        val date: Long,
        val main: Main,
        val wind: Wind,
        val weather: List<Weather>
    ) {
        constructor(weatherData: WeatherDataResponse) : this(
            id = weatherData.id,
            cityName = weatherData.cityName,
            date = weatherData.date,
            main = Main(weatherData.main),
            wind = Wind(weatherData.wind),
            weather = weatherData.weather.map(::Weather)
        )

        val time get() = DateFormat.format("MMM, dd yyyy", Date(date)).toString();
    }

    @JsonClass(generateAdapter = true)
    data class Weather(
        val id: String,
        val main: String,
        val description: String,
        val iconUrl: String
    ) {
        constructor(weather: WeatherResponse) : this(
            id = weather.id,
            main = weather.main,
            description = weather.description,
            iconUrl = weather.iconUrl
        )

        val itemIconUrl get() = "http://openweathermap.org/img/wn/$iconUrl.png"
        val mainItemIconUrl get() = "http://openweathermap.org/img/wn/$iconUrl@4x.png"
    }

    class Main(
        private val temp: Double,
        val feelsLike: Double,
        private val min: Double,
        private val max: Double,
        val pressure: String,
        val humidity: String
    ) {
        constructor(main: MainResponse) : this(
            temp = main.temp,
            feelsLike = main.feelsLike,
            min = main.min,
            max = main.max,
            pressure = main.pressure,
            humidity = main.humidity
        )

        val currentTemp get() = "${temp.roundTemp()}º"
        val currentMin get() = "${min.roundTemp()}º"
        val currentMax get() = "${max.roundTemp()}º"
        val currentFeel get() = "Feels like ${feelsLike.roundTemp()}º"
    }

    class Wind(
        val speed: Double,
        val deg: String
    ) {
        constructor(wind: WindResponse) : this(
            speed = wind.speed,
            deg = wind.deg
        )
    }
}

internal fun Double.roundTemp(): String {
    val formatted = NumberFormat.getNumberInstance()
    formatted.maximumFractionDigits = 0
    formatted.roundingMode = RoundingMode.HALF_EVEN
    return formatted.format(this)
}