package com.example.weather_info.weatherInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_info.model.CurrentWeather
import com.example.weather_info.model.Forecast
import com.example.weather_info.model.CurrentModel
import com.example.weather_info.model.TodayModel
import com.example.weather_info.model.WeeklyForecast
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

    private val _currentModel = MutableLiveData<CurrentModel>()

    val currentModel: LiveData<CurrentModel>
        get() = _currentModel

    private val _weeklyModels = MutableLiveData<List<WeeklyModel>>()

    val weeklyModels: LiveData<List<WeeklyModel>>
        get() = _weeklyModels

    private val _todayModels = MutableLiveData<List<TodayModel>>()

    val todayModels: LiveData<List<TodayModel>>
        get() = _todayModels


    private val _viewState = MutableLiveData<ViewState>()

    val viewState: LiveData<ViewState>
        get() = _viewState

    private var lat: Double = 0.0
    private var lon: Double = 0.0

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val repository: WeatherRepository by lazy {
        WeatherRepositoryImpl()
    }

    private val dayIdFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    private val todayDate = dayIdFormat.format(Date())
    private var dayNameFormat: SimpleDateFormat = SimpleDateFormat("EE", Locale.getDefault())
    private var dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
    private var timeFormat: SimpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
    private var mainDateFormat: SimpleDateFormat =
        SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())

    init {
        _viewState.value = ViewState.Loading
        setTimeZone()
    }

    private fun setTimeZone() {
        dayIdFormat.timeZone = TimeZone.getTimeZone("UTC")
        dayNameFormat.timeZone = TimeZone.getDefault()
        dateFormat.timeZone = TimeZone.getDefault()
        mainDateFormat.timeZone = TimeZone.getDefault()
        timeFormat.timeZone = TimeZone.getDefault()
    }

    fun setLatLon(lat: Double, lon: Double) {
        this.lat = lat
        this.lon = lon
        getWeather()
    }

    fun retry() {
        getWeather()
    }

    private fun getWeather() {
        _viewState.value = ViewState.Loading
        getCurrent()
        getForecast()
    }

    private fun getCurrent() {
        coroutineScope.launch {
            try {
                val currentWeather: CurrentWeather? = repository.getCurrentWeather(lat, lon)
                currentWeather?.let {

                    _viewState.value = ViewState.Success
                    _currentModel.value =
                        CurrentModel(
                            locationName = it.name,
                            time = it.timezone.toString(),
                            temp = it.main.temp.toString(),
                            icon = it.weather[0].icon
                        )
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Failure
            }
        }
    }

    private fun getForecast() {
        coroutineScope.launch {
            try {
                val weeklyResult: MutableList<WeeklyModel> = mutableListOf()
                var todayResult: MutableList<TodayModel> = mutableListOf()
                val forecast: WeeklyForecast? = repository.getWeatherForecast(lat, lon)

                if (forecast?.list != null && forecast.list.isNotEmpty()) {
                    // Group the forecasts by day
                    getForecastsGroupedByDay(forecast.list).forEach { (currentDay, dayForecasts) ->
                        if (dayForecasts.isNotEmpty()) {
                            // Use the third weather condition as representative for the whole day
                            // and if not available, then use the first one.
                            if (currentDay == todayDate) {
                                todayResult = getTodayModel(dayForecasts).toMutableList()
                            } else {
                                weeklyResult.add(getWeeklyModel(dayForecasts))
                            }
                        }
                    }
                }
                _viewState.value = ViewState.Success
                _weeklyModels.value = weeklyResult
                _todayModels.value = todayResult
            } catch (e: Exception) {
                _viewState.value = ViewState.Failure
            }
        }
    }

    private fun getWeeklyModel(
        dayForecasts: List<Forecast>
    ): WeeklyModel {
        val dayWeatherForecast =
            if (dayForecasts.size > 1) dayForecasts[1] else dayForecasts[0]
        return WeeklyModel(
            day = getDayName(dayForecasts[0].dt!! * 1000),
            date = getDate(dayForecasts[0].dt!! * 1000),
            icon = dayWeatherForecast.weather[0].icon,
            tempMax = getMaxTempFor(dayForecasts),
            tempMin = getMinTempFor(dayForecasts)
        )
    }

    private fun getTodayModel(
        dayForecasts: List<Forecast>
    ): List<TodayModel> {
        val todayResult: MutableList<TodayModel> = mutableListOf()
        dayForecasts.forEach {
            todayResult.add(
                TodayModel(
                    time = getTime(it.dt!! * 1000),
                    icon = it.weather[0].icon,
                    temp = it.main.temp.toString()
                )
            )
        }
        return todayResult
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getMaxTempFor(dayForecasts: List<Forecast>): String {
        var maxTemp: Double? = null
        dayForecasts.forEach {
            it.main.tempMax?.let { currentMax ->
                maxTemp = max(maxTemp ?: 0.0, currentMax)
            }
        }
        return maxTemp.toString()
    }

    private fun getMinTempFor(dayForecasts: List<Forecast>): String {
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

    private fun getTime(utcTimeMillis: Long): String {
        return timeFormat.format(Date(utcTimeMillis))
    }
}

enum class ViewState {
    Loading,
    Success,
    Failure
}