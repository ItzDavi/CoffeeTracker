package com.d.coffeetracker.arch

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d.coffeetracker.Constants
import com.d.coffeetracker.MyUtils

class CoffeeVM : ViewModel() {

    private val coffeeCount = MutableLiveData<Int>()
    private val coffeeDays = MutableLiveData<Int>()
    private val lastDate = MutableLiveData<String>()

    val totalStats = MutableLiveData<Stats>()
    val todayStats = MutableLiveData<Stats>()

    val lastChanged = MutableLiveData<CoffeeStats>()

    fun init(context: Context) {
        with (MyUtils) {
            var todaySmallCups = getFromSharedPrefs(context, Constants.SP_TODAY_SC)
            var todayMediumCups = getFromSharedPrefs(context, Constants.SP_TODAY_MC)
            var todayGrandeCups = getFromSharedPrefs(context, Constants.SP_TODAY_GC)

            val totalSmallCups = getFromSharedPrefs(context, Constants.SP_TOTAL_SC)
            val totalMediumCups = getFromSharedPrefs(context, Constants.SP_TOTAL_MC)
            val totalGrandeCups = getFromSharedPrefs(context, Constants.SP_TOTAL_GC)

            val daysCount = getFromSharedPrefs(context, Constants.SP_DAYS_COUNT)
            val todayDate = getTodayDate()

            if (getTodayDate() != lastDate.value) {
                todaySmallCups = "0"
                todayMediumCups = "0"
                todayGrandeCups = "0"

                resetTodayStats(context)
                saveTodayDate()
            }

            todayStats.postValue(Stats(daysCount, todayDate, todaySmallCups, todayMediumCups, todayGrandeCups))
            totalStats.postValue(Stats(daysCount, todayDate, totalSmallCups, totalMediumCups, totalGrandeCups))
        }
    }

    fun firstSetup(context: Context) {

        val zeroStats = Stats("0", MyUtils.getTodayDate(), "0", "0", "0")

        todayStats.postValue(zeroStats)
        totalStats.postValue(zeroStats)

        saveTodayDate()

        MyUtils.apply {
            saveToSharedPrefs(context, Constants.SP_FIRST_SETUP, true)

            saveToSharedPrefs(context, Constants.SP_TODAY_SC, 0)
            saveToSharedPrefs(context, Constants.SP_TODAY_MC, 0)
            saveToSharedPrefs(context, Constants.SP_TODAY_GC, 0)

            saveToSharedPrefs(context, Constants.SP_TOTAL_SC, 0)
            saveToSharedPrefs(context, Constants.SP_TOTAL_MC, 0)
            saveToSharedPrefs(context, Constants.SP_TOTAL_GC, 0)
        }
    }

    private fun resetTodayStats(context: Context) {
        with (MyUtils) {
            saveToSharedPrefs(context, Constants.SP_TODAY_SC, 0)
            saveToSharedPrefs(context, Constants.SP_TODAY_MC, 0)
            saveToSharedPrefs(context, Constants.SP_TODAY_GC, 0)
        }
    }

    fun addCoffee(context: Context, type: CoffeeSizes) {
        coffeeCount.postValue(coffeeCount.value?.plus(1) ?: 1)

        when (type) {
            CoffeeSizes.SMALL -> {
                val tempTodayStats = todayStats.value
                tempTodayStats?.apply {
                    smallCupsCount = (smallCupsCount.toInt() + 1).toString()
                }
                val tempTotalStats = totalStats.value
                tempTotalStats?.apply {
                    smallCupsCount = (smallCupsCount.toInt() + 1).toString()
                }

                with (MyUtils) {
                    saveToSharedPrefs(context, Constants.SP_TODAY_SC, tempTodayStats?.smallCupsCount)
                    saveToSharedPrefs(context, Constants.SP_TOTAL_SC, tempTotalStats?.smallCupsCount)
                }

                if (lastChanged.value?.name != type.name) {
                    lastChanged.postValue(CoffeeStats.SMALL)
                }

                updateTodayStats(tempTodayStats)
                updateTotalStats(tempTotalStats)
            }

            CoffeeSizes.MEDIUM -> {
                val tempTodayStats = todayStats.value
                tempTodayStats?.apply {
                    mediumCupsCount = (mediumCupsCount.toInt() + 1).toString()
                }
                val tempTotalStats = totalStats.value
                tempTotalStats?.apply {
                    mediumCupsCount = (mediumCupsCount.toInt() + 1).toString()
                }

                with (MyUtils) {
                    saveToSharedPrefs(context, Constants.SP_TODAY_MC, tempTodayStats?.mediumCupsCount)
                    saveToSharedPrefs(context, Constants.SP_TOTAL_MC, tempTotalStats?.mediumCupsCount)
                }

                if (lastChanged.value?.name != type.name) {
                    lastChanged.postValue(CoffeeStats.MEDIUM)
                }

                updateTodayStats(tempTodayStats)
                updateTotalStats(tempTotalStats)
            }

            CoffeeSizes.GRANDE -> {
                val tempTodayStats = todayStats.value
                tempTodayStats?.apply {
                    grandeCupsCount = (grandeCupsCount.toInt() + 1).toString()
                }
                val tempTotalStats = totalStats.value
                tempTotalStats?.apply {
                    grandeCupsCount = (grandeCupsCount.toInt() + 1).toString()
                }

                with (MyUtils) {
                    saveToSharedPrefs(context, Constants.SP_TODAY_GC, tempTodayStats?.grandeCupsCount)
                    saveToSharedPrefs(context, Constants.SP_TOTAL_GC, tempTotalStats?.grandeCupsCount)
                }

                if (lastChanged.value?.name != type.name) {
                    lastChanged.postValue(CoffeeStats.GRANDE)
                }

                updateTodayStats(tempTodayStats)
                updateTotalStats(tempTotalStats)
            }
        }
    }

    private fun updateTodayStats(newStats: Stats?) {
        newStats?.let {
            todayStats.postValue(it)
        }
    }

    private fun updateTotalStats(newStats: Stats?) {
        newStats?.let {
            totalStats.postValue(it)
        }
    }

    private fun saveTodayDate() {
        MyUtils.getTodayDate().apply {
            if (this != lastDate.value) {
                lastDate.postValue(this)
            }
        }
    }

    fun getTodayStats(): Stats? {
        return todayStats.value
    }

    fun getTotalStats(): Stats? {
        return totalStats.value
    }
}