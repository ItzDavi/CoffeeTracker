package com.d.coffeetracker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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

        if (MyUtils.getFromSharedPrefs(this, Constants.SP_FIRST_SETUP) == MyUtils.SharedPrefNotFound) {
            viewModel.firstSetup(this)
        } else {
            viewModel.init(this)
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
    }

    private fun setListeners() {
        val context = this

        setObservers()

        with (binding) {
            smallCupLayout.setOnClickListener {
                viewModel.addCoffee(context, CoffeeSizes.SMALL)
            }
            mediumCupLayout.setOnClickListener {
                viewModel.addCoffee(context, CoffeeSizes.MEDIUM)
            }
            grandeCupLayout.setOnClickListener {
                viewModel.addCoffee(context, CoffeeSizes.GRANDE)
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
                        "${stat.smallCupsCount.toInt() * CoffeeSizesML.SMALL.ml}ml".also { todayStatsDetailVolume.text = it }
                        "Cups: ${stat.smallCupsCount}".also { todayStatsDetailCount.text = it }
                    }
                    CoffeeStats.MEDIUM ->  {
                        todayStatsDetailImage.setImageResource(R.drawable.grande_cup)
                            "${stat.mediumCupsCount.toInt() * CoffeeSizesML.MEDIUM.ml}ml".also { todayStatsDetailVolume.text = it }
                        "Cups: ${stat.mediumCupsCount}".also { todayStatsDetailCount.text = it }
                    }
                    CoffeeStats.GRANDE ->  {
                        todayStatsDetailImage.setImageResource(R.drawable.medium_cup)
                        "${stat.grandeCupsCount.toInt() * CoffeeSizesML.GRANDE.ml}ml".also { todayStatsDetailVolume.text = it }
                        "Cups: ${stat.grandeCupsCount}".also { todayStatsDetailCount.text = it }
                    }
                }
            }
        }
    }

    private fun loadTotalStats() {
        val context = this

        with (binding) {
            viewModel.getTotalStats()?.let { stat ->
                "x${stat.smallCupsCount}".also { totalStatsSmallQuantity.text = it }
                "${stat.smallCupsCount.toInt() * CoffeeSizesML.SMALL.ml}ml".also { totalStatsSmallMl.text = it }

                "x${stat.mediumCupsCount}".also { totalStatsMediumQuantity.text = it }
                "${stat.mediumCupsCount.toInt() * CoffeeSizesML.MEDIUM.ml}ml".also { totalStatsMediumMl.text = it }

                "x${stat.grandeCupsCount}".also { totalStatsGrandeQuantity.text = it }
                "${stat.grandeCupsCount.toInt() * CoffeeSizesML.GRANDE.ml}ml".also { totalStatsGrandeMl.text = it }

                coffeeDays.text = MyUtils.getFromSharedPrefs(context, Constants.SP_DAYS_COUNT)
            }
        }
    }
}