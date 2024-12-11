package com.example.weather_info.network

import com.example.weather_info.BuildConfig
import com.example.weather_info.model.CurrentWeather
import com.example.weather_info.model.WeeklyForecast

class WeatherRepositoryImpl : WeatherRepository {

    private val weatherApi = WeatherApiImpl.weatherApiService

    companion object {
        private const val UNITS = "metric"
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeather? {
        return weatherApi
            .getCurrentWeatherAsync(lat, lon, UNITS, BuildConfig.WEATHER_API_APP_ID)
    }

    override suspend fun getWeatherForecast(lat: Double, lon: Double): WeeklyForecast? {
        return weatherApi
            .getWeatherForecastAsync(lat, lon, UNITS, BuildConfig.WEATHER_API_APP_ID)
    }
}
