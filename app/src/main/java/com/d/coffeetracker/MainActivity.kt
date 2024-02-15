package com.d.coffeetracker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.d.coffeetracker.arch.CoffeeSizes
import com.d.coffeetracker.arch.CoffeeSizesML
import com.d.coffeetracker.arch.CoffeeStats
import com.d.coffeetracker.arch.CoffeeVM
import com.d.coffeetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: CoffeeVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (MyUtils.getFromSharedPrefs(this, "first_setup") == MyUtils.SharedPrefNotFound) {
            firstSetup()
        }

        setListeners()

        loadUI()
    }

    override fun onResume() {
        super.onResume()
        resume()
    }

    private fun loadUI() {
        MyUtils.setStatusBarColor(this, window, R.color.background)

        with (binding) {
            lottieAnimation.playAnimation()
        }
    }

    private fun setListeners() {
        val context = this

        setObservers()

        with (binding) {
            smallCupLayout.setOnClickListener {
                synchronized(context) {
                    viewModel.addCoffee(CoffeeSizes.SMALL)
                }
            }
            mediumCupLayout.setOnClickListener {
                synchronized(context) {
                    viewModel.addCoffee(CoffeeSizes.MEDIUM)
                }
            }
            grandeCupLayout.setOnClickListener {
                synchronized(context) {
                    viewModel.addCoffee(CoffeeSizes.GRANDE)
                }
            }

            settings.setOnClickListener {
                startActivity(Intent(context, SettingsActivity::class.java))
                if (Build.VERSION.SDK_INT < 34) {
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }

            todayStatsSmall.setOnClickListener {
                loadStats(CoffeeStats.SMALL)
            }
            todayStatsMedium.setOnClickListener {
                loadStats(CoffeeStats.MEDIUM)
            }
            todayStatsGrande.setOnClickListener {
                loadStats(CoffeeStats.GRANDE)
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
                loadStats(CoffeeStats.TOTAL)
            }

            todayStats.observe(context) {
                Toast.makeText(context, "Fired observer", Toast.LENGTH_SHORT).show()
                loadStats(CoffeeStats.SMALL)
            }
        }
    }

    private fun loadStats(filter: CoffeeStats) {
        with (binding) {
            viewModel.getTodayStats()?.let { stat ->
                when (filter) {
                    CoffeeStats.TOTAL  ->  { loadTotalStats() }
                    CoffeeStats.SMALL  ->  {
                        todayStatsDetailImage.setImageResource(R.drawable.small_cup)
                        todayStatsDetailVolume.text = (stat.smallCups.size * CoffeeSizesML.SMALL.ml).toString()
                        "AAA: ${stat.smallCups.size}".also { todayStatsDetailCount.text = it }
                    }
                    CoffeeStats.MEDIUM ->  {
                        todayStatsDetailImage.setImageResource(R.drawable.grande_cup)
                        todayStatsDetailVolume.text = (stat.mediumCups.size * CoffeeSizesML.MEDIUM.ml).toString()
                        "BBB: ${stat.smallCups.size}".also { todayStatsDetailCount.text = it }
                    }
                    CoffeeStats.GRANDE ->  {
                        todayStatsDetailImage.setImageResource(R.drawable.medium_cup)
                        todayStatsDetailVolume.text = (stat.grandeCups.size * CoffeeSizesML.GRANDE.ml).toString()
                        "CCC: ${stat.smallCups.size}".also { todayStatsDetailCount.text = it }
                    }
                }
            }
        }
    }

    private fun loadTotalStats() {

    }

    private fun firstSetup() {
        val context = this

        MyUtils.apply {
            saveToSharedPrefs(context, "first_setup", true)

            saveToSharedPrefs(context, "today_small_cups", 0)
            saveToSharedPrefs(context, "today_medium_cups", 0)
            saveToSharedPrefs(context, "today_grande_cups", 0)

            saveToSharedPrefs(context, "total_small_cups", 0)
            saveToSharedPrefs(context, "total_medium_cups", 0)
            saveToSharedPrefs(context, "total_grande_cups", 0)
        }
    }
}