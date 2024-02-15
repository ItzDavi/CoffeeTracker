package com.d.coffeetracker.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d.coffeetracker.MyUtils

class CoffeeVM : ViewModel() {

    private val coffeeCount = MutableLiveData<Int>()
    private val coffeeDays = MutableLiveData<Int>()
    private val lastDate = MutableLiveData<String>()

    val totalStats = MutableLiveData<Stats>()
    val todayStats = MutableLiveData<Stats>()

    fun init() {
        saveTodayDate()

        val smallCupArrayList = arrayListOf(Cup(CoffeeSizes.SMALL, CoffeeSizesML.SMALL))
        val mediumCupArrayList = arrayListOf(Cup(CoffeeSizes.MEDIUM, CoffeeSizesML.MEDIUM))
        val grandeCupArrayList = arrayListOf(Cup(CoffeeSizes.GRANDE, CoffeeSizesML.GRANDE))
        val zeroStats = Stats(day = "1", date = MyUtils.getTodayDate(), smallCupArrayList, mediumCupArrayList, grandeCupArrayList)

        totalStats.postValue(zeroStats)
        todayStats.postValue(zeroStats)
    }

    fun addCoffee(type: CoffeeSizes) {
        coffeeCount.postValue(coffeeCount.value?.plus(1) ?: 1)

        when (type) {
            CoffeeSizes.SMALL -> {
                todayStats.value?.addCup(small = true)
            }

            CoffeeSizes.MEDIUM -> {
                todayStats.value?.addCup(medium = true)
            }

            CoffeeSizes.GRANDE -> {
                todayStats.value?.addCup(grande = true)
            }
        }
    }

    fun saveTodayDate() {
        MyUtils.getTodayDate().apply {
            if (this != lastDate.value) {
                lastDate.postValue(this)
            }
        }
    }

    fun getTodayStats(): Stats? {
        return todayStats.value
    }

    fun saveToTodayStats() {
    }

    fun saveToTotalStats() {
    }
}