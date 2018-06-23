package com.example.marllonfrizzo.clima.sync

import android.content.Context
import com.example.marllonfrizzo.clima.dados.ClimaContrato
import com.example.marllonfrizzo.clima.util.JsonUtils
import com.example.marllonfrizzo.clima.util.NetworkUtils

object ClimaSyncTask {

    @Synchronized
    fun sincronizarClima(context: Context) {
        try {
            val weatherRequestUrl = NetworkUtils.getUrl(context)

            println(weatherRequestUrl)

            val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl)

            val weatherValues = JsonUtils
                    .getWeatherContentValuesFromJson(context, jsonWeatherResponse)

            if (weatherValues != null && weatherValues.size != 0) {
                val sunshineContentResolver = context.contentResolver

                sunshineContentResolver.delete(ClimaContrato.Clima.CONTENT_URI, null, null)


                sunshineContentResolver
                        .bulkInsert(ClimaContrato.Clima.CONTENT_URI, weatherValues)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}