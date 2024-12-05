package com.example.weather_info.model

import com.google.gson.annotations.SerializedName

data class Cloud(
    @SerializedName("all") val all: Int?
)
