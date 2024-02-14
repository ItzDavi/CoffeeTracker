package com.d.coffeetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d.coffeetracker.arch.CoffeeVM
import com.d.coffeetracker.arch.CoffeeVM.CoffeeSizes
import com.d.coffeetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: CoffeeVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()

        loadUI()
    }

    override fun onResume() {
        super.onResume()
        resume()
    }

    private fun loadUI() {
        loadTodayStats()
        loadTotalStats()
    }

    private fun setListeners() {
        val context = this

        setObservers()

        with (binding) {
            smallCupLayout.setOnClickListener {
                viewModel.addCoffee(CoffeeSizes.SMALL)
            }

            mediumCupLayout.setOnClickListener {
                viewModel.addCoffee(CoffeeSizes.MEDIUM)
            }

            grandeCupLayout.setOnClickListener {
                viewModel.addCoffee(CoffeeSizes.GRANDE)
            }

            settings.setOnClickListener {
                startActivity(Intent(context, SettingsActivity::class.java))
            }
        }
    }

    private fun resume() {
        val context = this

        with (viewModel) {
            totalStats.removeObservers(context)
            todayStats.removeObservers(context)

            setObservers()
        }
    }

    private fun setObservers() {
        val context = this

        with (viewModel) {
            totalStats.observe(context) {
                loadTotalStats()
            }

            todayStats.observe(context) {
                loadTodayStats()
            }
        }
    }

    private fun loadTotalStats() {
    }

    private fun loadTodayStats() {
        viewModel.getTodayStats()?.let {

        }
    }
}