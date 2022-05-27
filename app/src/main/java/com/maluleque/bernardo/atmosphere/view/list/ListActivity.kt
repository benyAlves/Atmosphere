package com.maluleque.bernardo.atmosphere.view.list

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.maluleque.bernardo.atmosphere.R
import com.maluleque.bernardo.atmosphere.api.WeatherDataResponse
import com.maluleque.bernardo.atmosphere.utils.Status
import com.maluleque.bernardo.atmosphere.view.model.Cities.listOfCities
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {

    lateinit var weatherCurrentLocationView: View
    lateinit var weatherCurrentLocationLoadingView: View
    lateinit var weatherCurrentLocationErrorView: View
    lateinit var permissionErrorView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmer: View
    private lateinit var dateTextView: AppCompatTextView
    private lateinit var cityTextView: AppCompatTextView
    private lateinit var tempTextView: AppCompatTextView
    private lateinit var descriptionTextView: AppCompatTextView
    private lateinit var minMaxTextView: AppCompatTextView
    private lateinit var feelsLikeTextView: AppCompatTextView
    private lateinit var iconImageView: AppCompatImageView

    private val viewModel by viewModel<WeatherListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view_list)
        shimmer = findViewById(R.id.shimmer_view_container)
        weatherCurrentLocationLoadingView = findViewById(R.id.shimmer_view_current_location_weather)
        weatherCurrentLocationView = findViewById(R.id.current_location_weather)
        weatherCurrentLocationErrorView = findViewById(R.id.current_location_weather_error)
        permissionErrorView = findViewById(R.id.current_location_weather_permission_error)

        dateTextView = findViewById(R.id.text_view_date)
        cityTextView = findViewById(R.id.text_view_city_name)
        iconImageView = findViewById(R.id.image_view_icon)
        tempTextView = findViewById(R.id.text_view_temp)
        descriptionTextView = findViewById(R.id.text_view_weather_description)
        minMaxTextView = findViewById(R.id.text_view_min_max)
        feelsLikeTextView = findViewById(R.id.text_view_feels_like)


        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        fetchCitiesWeather()
        viewModel.getLocationLiveData().observe(this, Observer{
            getCurrentLocationWeather(
                latitude = it.latitude,
                longitude = it.longitude
            )
        })
        requestLocationPermission()
    }

    private fun getCurrentLocationWeather(latitude: String, longitude: String) {
        viewModel.fetchWeatherDetailsByCoordinates(latitude, longitude).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data -> onCurrentLocationSuccess(data)}
                    }
                    Status.ERROR -> {
                        onCurrentLocationError()
                    }
                    Status.LOADING -> {
                        onCurrentLocationLoading()
                    }
                }
            }
        })
    }

    private fun onCurrentLocationLoading() {
        permissionErrorView.isVisible = false
        weatherCurrentLocationErrorView.isVisible = false
        weatherCurrentLocationLoadingView.isVisible = true
        weatherCurrentLocationView.isVisible = false
    }

    private fun onCurrentLocationError() {
        permissionErrorView.isVisible = false
        weatherCurrentLocationErrorView.isVisible = true
        weatherCurrentLocationLoadingView.isVisible = false
        weatherCurrentLocationView.isVisible = false
    }

    private fun onCurrentLocationSuccess(data: WeatherInfo.WeatherData) {
        permissionErrorView.isVisible = false
        weatherCurrentLocationErrorView.isVisible = false
        weatherCurrentLocationLoadingView.isVisible = false
        weatherCurrentLocationView.isVisible = true

        cityTextView.text = data.cityName
        val description = data.weather.first().description
        descriptionTextView.text = description.replaceFirst(description[0], description[0].uppercaseChar())
        dateTextView.text = data.time
        tempTextView.text = data.main.currentTemp
        feelsLikeTextView.text = data.main.currentFeel
        minMaxTextView.text = "${data.main.currentMin}/${data.main.currentMax}"
        iconImageView.load(data.weather.first().mainItemIconUrl) {
            crossfade(true)
        }
    }

    private fun requestLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED){
            requestLocationUpdate()
        }else{
            permission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ hasPermission ->
        if (hasPermission){
            requestLocationUpdate()
        }else{
            permissionErrorView.isVisible = !hasPermission
            weatherCurrentLocationErrorView.isVisible = hasPermission
            weatherCurrentLocationLoadingView.isVisible = hasPermission
            weatherCurrentLocationView.isVisible = hasPermission
        }
    }
    private fun requestLocationUpdate() {
        viewModel.startLocationUpdate()
    }

    private fun fetchCitiesWeather() {
        viewModel.fetchWeatherDetailsByCodes(listOfCities).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data -> onSuccess(data.list)}
                    }
                    Status.ERROR -> {
                        onError()
                    }
                    Status.LOADING -> {
                        onLoading()
                    }
                }
            }
        }
        )
    }


    private fun onLoading() {
        recyclerView.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
    }

    private fun onSuccess(data: List<WeatherInfo.WeatherData>) {
        shimmer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = WeatherListAdapter(data, onClick = {

        })
    }

    private fun onError() {
        recyclerView.visibility = View.GONE
        shimmer.visibility = View.GONE
    }
}