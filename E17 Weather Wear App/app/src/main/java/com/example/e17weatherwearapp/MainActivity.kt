package com.example.e17weatherwearapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.e17weatherwearapp.databinding.ActivityMainBinding
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : Activity() {

    private val API_LINK: String = "https://api.openweathermap.org/data/2.5/weather?q="
    private val API_ICON: String = "https://openweathermap.org/img/w/"
    private val API_KEY: String = "5b510fbd945380c289158753541a6253"
    private val CITY = "Jyväskylä"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()

    }

    private fun loadData() {
        val url = "$API_LINK$CITY&APPID=$API_KEY&units=metric"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    weather(response)

                }
                catch (e: Exception){
                    e.printStackTrace()
                    Log.d("Weather", "***** error: $e")
                }
            },
            {error -> Log.d("Error", "Error: $error")}
        )
        val queue = Volley.newRequestQueue(this)
        queue.add(jsonObjectRequest)
    }

    private fun weather(response: JSONObject){

        val mainJSONObject = response.getJSONObject("main")
        val weatherArray = response.getJSONArray("weather")
        val firstWeatherObject = weatherArray.getJSONObject(0)

        val cityName = response.getString("name")
        val condition = firstWeatherObject.getString("main")
        val temperature = mainJSONObject.getString("temp") + " °C"

        val weatherTime : String = response.getString("dt")
        val weatherLong : Long = weatherTime.toLong()
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss")
        val dt = Instant.ofEpochSecond(weatherLong).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter).toString()

        val icon =findViewById<ImageView>(R.id.iconImageView)
        val weatherIcon = firstWeatherObject.getString("icon")
        val iconUrl = "$API_ICON$weatherIcon.png"

        findViewById<TextView>(R.id.cityTextView).text = cityName
        findViewById<TextView>(R.id.condTextView).text = condition
        findViewById<TextView>(R.id.tempTextView).text = temperature
        findViewById<TextView>(R.id.dtTextView).text = dt

        Glide
            .with(this)
            .asBitmap()
            .load(iconUrl)
            .into(icon)


    }
}