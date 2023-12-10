package com.github.sandeepyohans.textrunner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sandeepyohans.textrunner.databinding.ActivityDisplayBinding

class DisplayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.let {
            val msg = intent.extras?.getString("EXTRA_MSG")
            it.tvDisplay.text= msg.toString()
        }
    }
}