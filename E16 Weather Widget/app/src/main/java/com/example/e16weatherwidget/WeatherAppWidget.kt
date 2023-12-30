package com.example.e16weatherwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Implementation of App Widget functionality.
 */
class WeatherAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val views = RemoteViews(context.packageName, R.layout.weather_app_widget)
            views.setOnClickPendingIntent(R.id.cityTextView, pendingIntent)

            loadingWeatherForecast("Jyväskylä", context, views, appWidgetId, appWidgetManager)
        }

    }
    val API_LINK: String = "https://api.openweathermap.org/data/2.5/weather?q="
    val API_ICON: String = "https://openweathermap.org/img/w/"
    val API_KEY: String = "5b510fbd945380c289158753541a6253"
    val CITY = "Jyväskylä"

    private fun loadingWeatherForecast(
        city: String,
        context: Context,
        views: RemoteViews,
        appWidgetId: Int,
        appWidgetManager: AppWidgetManager
    ) {
        val url = "$API_LINK$CITY&APPID=$API_KEY&units=metric"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val mainJSONObject = response.getJSONObject("main")
                    val weatherArray = response.getJSONArray("weather")
                    val firstWeatherObject = weatherArray.getJSONObject(0)
                    // city, condition, temperature
                    val cityName = response.getString("name")
                    val condition = firstWeatherObject.getString("main")
                    val temperature = mainJSONObject.getString("temp") + " °C"
                    // time
                    val weatherTime: String = response.getString("dt")
                    val weatherLong: Long = weatherTime.toLong()
                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss")
                    val dt = Instant.ofEpochSecond(weatherLong).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter).toString()

                    views.setTextViewText(R.id.cityTextView, cityName)
                    views.setTextViewText(R.id.condTextView, condition)
                    views.setTextViewText(R.id.tempTextView, temperature)
                    views.setTextViewText(R.id.timeTextView, dt)

                    val awt: AppWidgetTarget = object : AppWidgetTarget(context.applicationContext, R.id.iconImageView, views, appWidgetId) {}
                    val weatherIcon = firstWeatherObject.getString("icon")
                    val iconUrl = "$API_ICON$weatherIcon.png"

                    Glide
                        .with(context)
                        .asBitmap()
                        .load(iconUrl)
                        .into(awt)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("WEATHER", "**** error: $e")
                } finally {
                    // Move the updateAppWidget call outside of the try block
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            },
            { error -> Log.d("ERROR", "Error: $error") }
        )

        val queue = Volley.newRequestQueue(context)
        queue.add(jsonObjectRequest)
    }



    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.weather_app_widget)
    // views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}