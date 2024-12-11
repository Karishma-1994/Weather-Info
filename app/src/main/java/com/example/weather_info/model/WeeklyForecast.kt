package com.example.weather_info.model

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("cod") val cod: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("cnt") val cnt: Int?,
    @SerializedName("city") val city: City?,
    @SerializedName("list") val list: List<Forecast>
)
