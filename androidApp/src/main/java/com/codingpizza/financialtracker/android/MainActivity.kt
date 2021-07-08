package com.codingpizza.financialtracker.android

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv: TextView = findViewById(R.id.text_view)

        val viewModel: MainViewModel by viewModels()
        viewModel.getData()
        viewModel.liveData.observe(this) {
            tv.text = it
        }

    }
}
