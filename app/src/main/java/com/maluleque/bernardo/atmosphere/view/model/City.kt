package com.maluleque.bernardo.atmosphere.view.model

class City(
    val id: String,
    val name: String
)

object Cities{
      val listOfCities = listOf(
            City("2267057", "Lisbon"),
            City("3117735", "Madrid"),
            City("6455259", "Paris"),
            City("4348460", "Berlin"),
            City("2618425", "Copenhagen"),
            City("6127261", "Roma"),
            City("3846616", "London"),
            City("4192205", "Dublin"),
            City("4548393", "Prague"),
            City("4739232", "Vienna"))
}