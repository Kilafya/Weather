package com.example.weather

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.filament.View
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var resultInfo: TextView? = null
    private var mainText: TextView? = null
    private var cityName: EditText? = null
    private var requestButton: Button? = null
    val text : View? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultInfo = findViewById(R.id.result_info)
        mainText = findViewById(R.id.main_text)
        cityName = findViewById(R.id.city_name)
        requestButton = findViewById(R.id.request_button)

        requestButton?.setOnClickListener {
            val text = cityName?.text ?: return@setOnClickListener
            if (text.toString().trim().isEmpty()) {
                Toast.makeText(this, R.string.support_toast, Toast.LENGTH_LONG).show()
            } else {
                val city = text.toString().trim()
                val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"
                doAsync {
                    val apiResponse = URL(url).readText()
                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val desc = weather.getJSONObject(0).getString("description").capitalize()
                    val temp = JSONObject(apiResponse).getJSONObject("main").getString("temp")

                    resultInfo?.text = "$desc\nТемпература: $temp"
                    Log.i("JSON_DATA", apiResponse)
                }
            }
        }
    }
}

