package com.maluleque.bernardo.atmosphere.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.maluleque.bernardo.atmosphere.R
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo
import java.math.RoundingMode
import java.text.NumberFormat

class WeatherListAdapter(
    private val list: List<WeatherInfo.WeatherData>,
    private val onClick: (WeatherInfo.WeatherData) -> Unit
    ) : RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

        class WeatherViewHolder(view: View, val onClick: (WeatherInfo.WeatherData) -> Unit) :
            RecyclerView.ViewHolder(view) {

            private val textViewCity: AppCompatTextView = view.findViewById(R.id.text_view_city_name)
            private val textViewTemp: AppCompatTextView = view.findViewById(R.id.text_view_temp)
            private val textViewDescription: AppCompatTextView = view.findViewById(R.id.text_view_weather_description)
            private val textViewMinMax: AppCompatTextView = view.findViewById(R.id.text_view_min_max)
            private val imageView: AppCompatImageView = view.findViewById(R.id.image_view_icon)
            private var weatherResponse: WeatherInfo.WeatherData? = null

            init {
                view.setOnClickListener {
                    weatherResponse?.let {
                        onClick(it)
                    }
                }
            }


            fun bind(weather: WeatherInfo.WeatherData) {
                weatherResponse = weather
                textViewCity.text = weather.cityName
                textViewDescription.text = weather.weather.first().description
                textViewMinMax.text = "${weather.main.currentMin}/${weather.main.currentMax}"
                textViewTemp.text = weather.main.currentTemp
                imageView.load(weather.weather.first().itemIconUrl) {
                    crossfade(true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder =
            WeatherViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false), onClick
            )

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
            holder.bind(list[position])
        }

    }


