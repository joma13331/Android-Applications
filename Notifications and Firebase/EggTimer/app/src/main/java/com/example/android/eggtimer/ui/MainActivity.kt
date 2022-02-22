package com.example.android.eggtimer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.eggtimer.R
import com.example.android.eggtimer.ui.EggTimerFragment.Companion.newInstance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, newInstance())
                .commitNow()
        }
    }
}