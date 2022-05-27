package com.maluleque.bernardo.atmosphere.view.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import coil.load
import com.maluleque.bernardo.atmosphere.R
import com.maluleque.bernardo.atmosphere.view.EXTRA_WEATHER_DATA
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo

class DetailsActivity : AppCompatActivity() {

    private lateinit var dateTextView: AppCompatTextView
    private lateinit var cityTextView: AppCompatTextView
    private lateinit var tempTextView: AppCompatTextView
    private lateinit var descriptionTextView: AppCompatTextView
    private lateinit var minMaxTextView: AppCompatTextView
    private lateinit var feelsLikeTextView: AppCompatTextView
    private lateinit var humidityTextView: AppCompatTextView
    private lateinit var windTextView: AppCompatTextView
    private lateinit var pressureTextView: AppCompatTextView
    private lateinit var iconImageView: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val weatherData = intent.getParcelableExtra<WeatherInfo.WeatherData>(EXTRA_WEATHER_DATA)

        dateTextView = findViewById(R.id.text_view_date)
        cityTextView = findViewById(R.id.text_view_city_name)
        iconImageView = findViewById(R.id.image_view_icon)
        tempTextView = findViewById(R.id.text_view_temp)
        descriptionTextView = findViewById(R.id.text_view_weather_description)
        minMaxTextView = findViewById(R.id.text_view_min_max)
        feelsLikeTextView = findViewById(R.id.text_view_feels_like)
        humidityTextView = findViewById(R.id.text_view_humidity)
        windTextView = findViewById(R.id.text_view_wind)
        pressureTextView = findViewById(R.id.text_view_pressure)

        bind(weatherData)
    }

    private fun bind(data: WeatherInfo.WeatherData?) {
        data?.let {
            cityTextView.text = data.cityName
            val description = data.weather.first().description
            descriptionTextView.text = description.replaceFirst(
                description.first(),
                description.first().uppercaseChar()
            )
            dateTextView.text = data.time
            tempTextView.text = data.main.currentTemp
            feelsLikeTextView.text = data.main.currentFeel
            minMaxTextView.text = "${data.main.currentMin}/${data.main.currentMax}"

            humidityTextView.text = "${data.main.humidity}%"
            windTextView.text = "${data.wind.speed} meter/sec"
            pressureTextView.text = "${data.main.pressure} mb"
            iconImageView.load(data.weather.first().mainItemIconUrl) {
                crossfade(true)
            }
        }
    }
}