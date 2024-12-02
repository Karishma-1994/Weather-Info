package com.example.weather_info.weatherInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_info.model.TodayModel
import com.example.weather_info.model.WeeklyModel

class WeatherViewModel : ViewModel() {

    private val _todayModelList = MutableLiveData<List<TodayModel>>()

    val todayModelList: LiveData<List<TodayModel>>
        get() = _todayModelList

    private val _weeklyModelList = MutableLiveData<List<WeeklyModel>>()

    val weeklyModelList: LiveData<List<WeeklyModel>>
        get() = _weeklyModelList

    init {
        setupTodayList()
        setupWeeklyList()
    }

    private fun setupTodayList() {
        val todayModelList: MutableList<TodayModel> = mutableListOf()
        for (i in 1..10) {
            todayModelList.add(TodayModel(i, "time $i", "temp $i"))
        }
        _todayModelList.value = todayModelList
    }

    private fun setupWeeklyList() {
        val weeklyModelList: MutableList<WeeklyModel> = mutableListOf()
        for (i in 1..10) {
            weeklyModelList.add(WeeklyModel(i, "day $i", "temp $i"))
        }
        _weeklyModelList.value = weeklyModelList
    }
}