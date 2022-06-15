package com.mrmukto.climeforecast.repos


import android.location.Location
import com.mrmukto.climeforecast.NetworkService
import com.mrmukto.climeforecast.models.CurrentModel
import com.mrmukto.climeforecast.models.ForecastModel
import com.mrmukto.climeforecast.weather_api_key

class WeatherRepository {
    suspend fun fetchCurrentData(location: Location,status: Boolean): CurrentModel {
        val  unit = if (status) "imperial" else "metric"
        val endUrl =
            "weather?lat=${location.latitude}&lon=${location.longitude}&units=$unit&appid=${weather_api_key}"
        return NetworkService.weatherServiceApi
            .gerCurrentWeather(endUrl)
    }
    suspend fun fetchForecastData(location: Location,status: Boolean): ForecastModel {
        val unit = if (status) "imperial" else "metric"
        val endUrl =
            "forecast?lat=${location.latitude}&lon=${location.longitude}&units=$unit&appid=${weather_api_key}"
        return NetworkService.weatherServiceApi
            .gerForecastWeather(endUrl)
    }



}