package com.example.weather_info.network

import com.example.weather_info.model.CurrentWeather
import com.example.weather_info.model.WeatherForecast

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeather?
    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherForecast?
}