package com.example.e07myfirstappwithnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class MessageScreen : Fragment(R.layout.fragment_message_screen) {

    private val args: MessageScreenArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_message_screen, container, false)

        // Set args message
        val textField = view.findViewById<TextView>(R.id.setMessage)
        textField.text = args.message

        return view
    }
}
