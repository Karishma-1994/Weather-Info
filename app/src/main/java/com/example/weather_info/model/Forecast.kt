package com.example.weather_info.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("dt") val dt: Long?,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("cloud") val cloud: Cloud?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("pop") val pop: Double?,
    @SerializedName("rain") val rain: Rain?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("dt_txt") val dtTxt: String?
)
