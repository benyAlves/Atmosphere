package com.maluleque.bernardo.atmosphere.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.maluleque.bernardo.atmosphere.repository.WeatherRepository
import com.maluleque.bernardo.atmosphere.utils.Resource
import com.maluleque.bernardo.atmosphere.view.model.City
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo.WeatherResponseInfo

class WeatherListViewModel (private val repository: WeatherRepository) : ViewModel() {

    fun fetchWeatherDetailsByCodes(codes: List<City>) = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = WeatherResponseInfo(repository.fetchCityWeatherByCode(codes))))
        } catch (exception: Exception) {
            emit(Resource.error(message = exception.message ?: "Ups something went wrong!"))
        }
    }
}