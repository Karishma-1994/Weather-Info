package com.example.weather_info.util

import android.widget.ImageView
import com.example.weather_info.R
import com.squareup.picasso.Picasso


fun ImageView.loadWeatherIcon(weatherIcon: String) {
    try {
        //Did to prevent crash when picasso is mocked then load function is not present
        Picasso.get()
            .load(context.getString(R.string.weather_image_url, weatherIcon))
            .into(this)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }
}