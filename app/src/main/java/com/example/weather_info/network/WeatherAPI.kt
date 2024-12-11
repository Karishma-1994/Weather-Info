package com.example.weather_info.network

import com.example.weather_info.model.CurrentWeather
import com.example.weather_info.model.WeatherForecast
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    // Docs: https://openweathermap.org/current
    // Example: http://api.openweathermap.org/data/2.5/weather?lat=19.075983&lon=72.877655&units=metric&appid=b707c85fb1ca3d79f0e93158c156c56a
    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): CurrentWeather

    // Docs: https://openweathermap.org/forecast5
    // Example: http://api.openweathermap.org/data/2.5/forecast?lat=19.075983&lon=72.877655&units=metric&appid=b707c85fb1ca3d79f0e93158c156c56a
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecastAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): WeatherForecast
}


object WeatherApiImpl {
    val weatherApiService: WeatherAPI by lazy {
        retrofit.create(WeatherAPI::class.java)
    }
}