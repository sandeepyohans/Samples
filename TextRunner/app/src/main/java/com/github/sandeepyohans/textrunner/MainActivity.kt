package com.github.sandeepyohans.textrunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.sandeepyohans.textrunner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btDisplay.setOnClickListener {
            val textDisplay = binding.edtInput.text.toString()
            if(textDisplay.isNotBlank() || textDisplay.isNotEmpty() ) {
                val intent = Intent(this, DisplayActivity::class.java).apply {
                    putExtra("EXTRA_MSG", textDisplay)
                }
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "No text found to display! Please re-enter text message.",
                    Toast.LENGTH_SHORT )
                    .show()
            }
        }
    }

}