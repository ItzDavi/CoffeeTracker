package com.d.coffeetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d.coffeetracker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun loadUI() {

    }

    private fun setListeners() {
        with (binding) {
            back.setOnClickListener {
                finish()
            }
        }
    }
}