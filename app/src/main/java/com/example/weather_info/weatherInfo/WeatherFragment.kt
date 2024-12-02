package com.example.weather_info.weatherInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.example.weather_info.R
import com.example.weather_info.databinding.FragmentWeatherBinding
import com.example.weather_info.weatherInfo.adapter.TodayAdapter
import com.example.weather_info.weatherInfo.adapter.WeeklyAdapter

class WeatherFragment : Fragment() {

    private lateinit var todayAdapter: TodayAdapter
    private lateinit var weeklyAdapter: WeeklyAdapter
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentWeatherBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_weather, container, false
        )

        binding.lifecycleOwner = this
        binding.weatherViewModel = viewModel

        todayAdapter = TodayAdapter()
        binding.rvToday.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvToday.adapter = todayAdapter

        binding.rvWeekly.layoutManager = LinearLayoutManager(context)
        weeklyAdapter = WeeklyAdapter()

        binding.rvWeekly.adapter = weeklyAdapter

        todayAdapter.setTodays(viewModel.todayModelList.value!!)
        weeklyAdapter.setWeekly(viewModel.weeklyModelList.value!!)

        return binding.root
    }
}