package com.claire.carddiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.claire.carddiary.base.ActivityBindingProvider
import com.claire.carddiary.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by ActivityBindingProvider(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}