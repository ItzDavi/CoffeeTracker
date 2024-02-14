package com.d.coffeetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.d.coffeetracker.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUI()

        setListeners()
    }

    override fun onResume() {
        super.onResume()
        resume()
    }

    private fun loadUI() {
        lifecycleScope.launch {
            loadTodayStats()
            loadTotalStats()
        }
    }

    private fun setListeners() {
        with (binding) {
            smallCupLayout.setOnClickListener {

            }

            mediumCupLayout.setOnClickListener {

            }

            grandeCupLayout.setOnClickListener {

            }

            settings.setOnClickListener {

            }
        }
    }

    private fun resume() {

    }

    private suspend fun loadTotalStats() {

    }

    private suspend fun loadTodayStats() {

    }
}