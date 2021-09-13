package com.eylonheimann.motiv8ai.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eylonheimann.motiv8ai.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}