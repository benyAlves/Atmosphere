package com.maluleque.bernardo.atmosphere.di

import com.maluleque.bernardo.atmosphere.api.RetrofitService
import com.maluleque.bernardo.atmosphere.repository.WeatherRepository
import com.maluleque.bernardo.atmosphere.view.list.WeatherListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


    val appModule = module {
        single { RetrofitService }
        factory { get<RetrofitService>().getWeatherApi() }
        factory { WeatherRepository(get()) }
        viewModel { WeatherListViewModel(get(), androidContext()) }
    }
