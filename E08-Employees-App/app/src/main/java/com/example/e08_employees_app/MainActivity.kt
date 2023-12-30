package com.example.e08_employees_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadJSON()
    }

    private fun loadJSON() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ptm.fi/data/android_employees.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val employees = response.getJSONArray("employees")
                Log.d("JSON", employees.toString())
                recyclerView.adapter  = EmployeesAdapter(employees)
            },
            {
                error ->
                Log.d("JSON", error.toString())
            }
        )
        queue.add(jsonObjectRequest)
    }
}
