package com.example.weather_info.model

import android.opengl.Visibility
import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("dt") val dt: Int?,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("cloud") val cloud: Cloud?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("pop") val pop: Double?,
    @SerializedName("visibility") val visibility: Visibility?,
    @SerializedName("rain") val rain: Rain?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("dt_txt") val dt_txt: Double?
)
