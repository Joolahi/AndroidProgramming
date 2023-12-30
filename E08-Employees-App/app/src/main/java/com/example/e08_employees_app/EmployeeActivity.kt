package com.example.e08_employees_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import org.json.JSONObject

class EmployeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val employeeString = bundle.getString("employee")
            if (employeeString != null) {
                val employee = JSONObject(employeeString)
                findViewById<TextView>(R.id.detailName).text = "${employee["firstName"]} ${employee["lastName"]}"
                findViewById<TextView>(R.id.detailTitle).text = employee["title"].toString()
                findViewById<TextView>(R.id.detailEmail).text = employee["email"].toString()
                findViewById<TextView>(R.id.detailPhone).text = employee["phone"].toString()
                findViewById<TextView>(R.id.detailDepartment).text = employee["department"].toString()

                val img = findViewById<ImageView>(R.id.detailImage)
                Glide
                    .with(img)
                    .load(employee["image"].toString())
                    .into(img)

            }
        }
    }
}