package com.example.weather_info

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.weather_info.databinding.ActivityMainBinding
import com.example.weather_info.weatherInfo.WeatherFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val weatherFragment = WeatherFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, weatherFragment)
            .commitAllowingStateLoss()
    }
}