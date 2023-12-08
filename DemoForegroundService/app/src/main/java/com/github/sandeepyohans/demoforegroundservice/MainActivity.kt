package com.github.sandeepyohans.demoforegroundservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sandeepyohans.demoforegroundservice.service.ForegroundService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onStart() {
        super.onStart()
        ForegroundService.startService(
            this, "some string you want to pass into the service"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        ForegroundService.stopService(this)
    }
}