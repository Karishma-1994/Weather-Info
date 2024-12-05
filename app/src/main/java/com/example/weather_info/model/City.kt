package com.example.weather_info.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("population") val population: Int?,
    @SerializedName("coord") val coord: Coordinates,
    @SerializedName("country") val country: String?,
    @SerializedName("sunrise") val sunrise: Int?,
    @SerializedName("sunset") val sunset: Int?,
    @SerializedName("timezone") val timezone: Int?
)