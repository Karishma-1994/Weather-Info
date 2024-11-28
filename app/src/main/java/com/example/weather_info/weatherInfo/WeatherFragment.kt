package com.example.weather_info.weatherInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weather_info.R
import com.example.weather_info.databinding.ActivityMainBinding
import com.example.weather_info.databinding.FragmentWeatherBinding
import com.example.weather_info.weatherInfo.adapter.TodayAdapter

class WeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentWeatherBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_weather, container, false
        )



        return binding.root
    }
}