package com.maluleque.bernardo.atmosphere.view.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.maluleque.bernardo.atmosphere.repository.WeatherRepository
import com.maluleque.bernardo.atmosphere.utils.Resource
import com.maluleque.bernardo.atmosphere.view.model.City
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo.WeatherResponseInfo
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo.WeatherData

class WeatherListViewModel (
    private val repository: WeatherRepository, context: Context
    ) : ViewModel() {

    private val locationLiveData = LocationLiveData(context)
    fun getLocationLiveData() = locationLiveData

    fun startLocationUpdate(){
        locationLiveData.startLocationUpdate()
    }

    fun fetchWeatherDetailsByCodes(codes: List<City>) = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = WeatherResponseInfo(repository.fetchCityWeatherByCode(codes))))
        } catch (exception: Exception) {
            emit(Resource.error(message = exception.message ?: "Ups something went wrong!"))
        }
    }

    fun fetchWeatherDetailsByCoordinates(latitude: String, longitude: String) = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = WeatherData(repository.fetchCityWeatherByCoordinates(
                        latitude = latitude,
                        longitude = longitude
                    ))
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(message = exception.message ?: "Ups something went wrong!"))
        }
    }

}