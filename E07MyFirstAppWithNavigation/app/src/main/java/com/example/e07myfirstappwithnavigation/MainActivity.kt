package com.example.myfirstappwithnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
    }

    fun navigateToSecondFragment(view : View) {
        val messageView : TextView = findViewById(R.id.txtMessageInput)
        val message = messageView.text.toString()
        val action = fragmentMainDirections.actionFragmentMainToSecondFragment2(message)
        view.findNavController().navigate(action)
    }
}

