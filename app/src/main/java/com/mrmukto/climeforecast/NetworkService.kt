package com.mrmukto.climeforecast

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.mrmukto.climeforecast.models.CurrentModel
import com.mrmukto.climeforecast.models.ForecastModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.text.SimpleDateFormat
import java.util.*

const val weather_api_key = "487c1be02c9bd3001ee89448ba371f9c"
const val base_url = "https://api.openweathermap.org/data/2.5/"
const val icon_prefix = "https://openweathermap.org/img/wn/"
const val icon_suffix = "@2x.png"

fun getFormattedDate(dt: Long, pattern: String) : String {
        return SimpleDateFormat(pattern).format(Date(dt * 1000))
}
@SuppressLint("MissingPermission")
fun getLocation(context: Context, callback: (Location) -> Unit) {
        val locationProvider = FusedLocationProviderClient(context)
        locationProvider.lastLocation
                .addOnCompleteListener {
                        if (it.isSuccessful) {
                                val location = it.result
                                location?.let {
                                        callback(it)
                                }
                        }
                }
}



val retrofit= Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

interface WeatherServiceApi{
        @GET
        suspend fun gerCurrentWeather(@Url endUrl: String) :CurrentModel

        @GET
        suspend fun gerForecastWeather(@Url endUrl: String) :ForecastModel
}
object NetworkService {
        val weatherServiceApi : WeatherServiceApi by lazy {
                retrofit.create(WeatherServiceApi::class.java)
        }
}