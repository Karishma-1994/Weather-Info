package com.example.weather_info.network

import com.example.weather_info.model.CurrentWeather
import com.example.weather_info.model.WeeklyForecast

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeather?
    suspend fun getWeatherForecast(lat: Double, lon: Double): WeeklyForecast?
}