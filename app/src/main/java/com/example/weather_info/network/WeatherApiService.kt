package com.example.weather_info.network

import com.example.weather_info.BuildConfig
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val gson = Gson()

val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BuildConfig.WEATHER_API_ENDPOINT)
    .build()

