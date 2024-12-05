package com.example.weather_info.network

import com.google.gson.Gson
import retrofit2.Retrofit


private const val BASE_URL = "http://api.openweathermap.org/"
private const val API_APP_ID = "d24a2df07606c067c0f768bdc2cc10a5"

private val gson = Gson()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()

