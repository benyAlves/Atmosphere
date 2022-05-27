package com.maluleque.bernardo.atmosphere.view.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluleque.bernardo.atmosphere.R
import com.maluleque.bernardo.atmosphere.utils.Status
import com.maluleque.bernardo.atmosphere.view.model.Cities.listOfCities
import com.maluleque.bernardo.atmosphere.view.model.WeatherInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    private val viewModelList by viewModel<WeatherListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view_list)
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        fetchCitiesWeather()
    }

    private fun fetchCitiesWeather() {
        viewModelList.fetchWeatherDetailsByCodes(listOfCities).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data -> onSuccess(data.list)}
                    }
                    Status.ERROR -> {
                        onError("")
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
    }

    private fun onSuccess(data: List<WeatherInfo.WeatherData>) {
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = WeatherListAdapter(data, onClick = {

        })
    }

    private fun onError(message: String?) {
        recyclerView.visibility = View.GONE
    }
}