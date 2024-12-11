package com.example.weather_info.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("coord") val coord: Coordinates,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("base") val base: String?,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("rain") val rain: Rain?,
    @SerializedName("cloud") val cloud: Cloud?,
    @SerializedName("dt") val dt: Int?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("cod") val cod: Int?,
    @SerializedName("timezone") val timezone: Int?
)
