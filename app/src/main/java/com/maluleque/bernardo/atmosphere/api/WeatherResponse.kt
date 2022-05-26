package com.maluleque.bernardo.atmosphere.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "cnt")
    val count: Int,
    @Json(name = "list")
    val list: List<WeatherData>
)

@JsonClass(generateAdapter = true)
data class WeatherData(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val cityName: String,
    @Json(name = "main")
    val main: Main,
    @Json(name = "wind")
    val wind: Wind,
    @Json(name = "weather")
    val weather: List<Weather>
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: String,
    @Json(name = "main")
    val main: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val iconUrl: String
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "feels_like")
    val feelsLike: String,
    @Json(name = "temp_min")
    val min: Double,
    @Json(name = "temp_max")
    val max: Double,
    @Json(name = "pressure")
    val pressure: String,
    @Json(name = "humidity")
    val humidity: String
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed")
    val speed: Double,
    @Json(name = "deg")
    val deg: String
)