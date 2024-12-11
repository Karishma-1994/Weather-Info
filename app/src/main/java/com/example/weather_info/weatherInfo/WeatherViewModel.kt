package com.example.weather_info.weatherInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_info.model.CurrentWeather
import com.example.weather_info.model.Forecast
import com.example.weather_info.model.TodayModel
import com.example.weather_info.model.WeatherForecast
import com.example.weather_info.model.WeeklyModel
import com.example.weather_info.network.WeatherRepository
import com.example.weather_info.network.WeatherRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.max
import kotlin.math.min

class WeatherViewModel : ViewModel() {

    private val _todayModel = MutableLiveData<TodayModel>()

    val todayModel: LiveData<TodayModel>
        get() = _todayModel

    private val _weeklyModels = MutableLiveData<List<WeeklyModel>>()

    val weeklyModels: LiveData<List<WeeklyModel>>
        get() = _weeklyModels

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val repository: WeatherRepository by lazy {
        WeatherRepositoryImpl()
    }

    private val dayIdFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    private var dayNameFormat: SimpleDateFormat = SimpleDateFormat("EE", Locale.getDefault())
    private var dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
    private var mainDateFormat: SimpleDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())

    init {
        getToday()
        getWeeklyList()
        setTimeZone()
    }

    private fun setTimeZone() {
        dayIdFormat.timeZone = TimeZone.getTimeZone("UTC")
        dayNameFormat.timeZone = TimeZone.getDefault()
        dateFormat.timeZone = TimeZone.getDefault()
        mainDateFormat.timeZone = TimeZone.getDefault()
    }

    private fun getToday() {
        coroutineScope.launch {
            val currentWeather: CurrentWeather? = repository.getCurrentWeather(12.955967, 77.656002)
            currentWeather?.let {
                _todayModel.value =
                    TodayModel(
                        locationName = it.name,
                        time = it.timezone.toString(),
                        temp = "temp ${it.main.temp}",
                        icon = it.weather[0].icon
                    )
            }
        }
    }

    private fun getWeeklyList() {
        coroutineScope.launch {
            val result: MutableList<WeeklyModel> = mutableListOf()
            val forecast: WeatherForecast? =
                repository.getWeatherForecast(12.955967, 77.656002)
            if (forecast?.list != null && forecast.list.isNotEmpty()) {
                // Group the forecasts by day
                getForecastsGroupedByDay(forecast.list).forEach { (_, dayForecasts) ->
                    if (dayForecasts.isNotEmpty()) {
                        // Use the third weather condition as representative for the whole day
                        // and if not available, then use the first one.
                        val dayWeatherForecast =
                            if (dayForecasts.size > 2) dayForecasts[2] else dayForecasts[0]
                        result.add(
                            WeeklyModel(
                                day = getDayName(dayForecasts[0].dt!! * 1000),
                                date = getDate(dayForecasts[0].dt!! * 1000),
                                icon = dayWeatherForecast.weather[0].icon,
                                tempMax = getMaxTempFor(dayForecasts),
                                tempMin = getMinTempFor(dayForecasts)
                            )
                        )
                    }
                }
            }
            _weeklyModels.value = result
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getMaxTempFor(dayForecasts: List<Forecast>): String? {
        var maxTemp: Double? = null
        dayForecasts.forEach {
            it.main.tempMax?.let { currentMax ->
                maxTemp = max(maxTemp ?: 0.0, currentMax)
            }
        }
        return maxTemp.toString()
    }

    private fun getMinTempFor(dayForecasts: List<Forecast>): String? {
        var minTemp: Double? = null
        dayForecasts.forEach {
            it.main.tempMin?.let { currentMin ->
                minTemp = if (minTemp == null) currentMin else min(minTemp ?: 0.0, currentMin)
            }
        }
        return minTemp.toString()
    }

    private fun getForecastsGroupedByDay(forecasts: List<Forecast>): Map<String, List<Forecast>> {
        return forecasts
            .filter { it.dt != null }
            .groupBy { getDayId(it.dt!! * 1000) }
    }

    private fun getDayId(utcTimeMillis: Long): String {
        return dayIdFormat.format(Date(utcTimeMillis))
    }

    private fun getDayName(utcTimeMillis: Long): String {
        return dayNameFormat.format(Date(utcTimeMillis))
    }

    private fun getDate(utcTimeMillis: Long): String {
        return dateFormat.format(Date(utcTimeMillis))
    }
}