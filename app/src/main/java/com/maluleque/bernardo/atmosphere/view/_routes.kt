package com.maluleque.bernardo.atmosphere.view

import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.maluleque.bernardo.atmosphere.view.details.DetailsActivity
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo

internal const val EXTRA_WEATHER_DATA = "weather"


fun Context.intentToDetails(weatherData: WeatherInfo.WeatherData) =
    Intent(this, DetailsActivity::class.java)
        .putExtra(EXTRA_WEATHER_DATA, weatherData)

fun Context.intentToLocationSettings() = Intent(
    Settings.ACTION_LOCATION_SOURCE_SETTINGS
)
