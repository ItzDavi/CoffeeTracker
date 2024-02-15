package com.d.coffeetracker.arch

import androidx.lifecycle.MutableLiveData
import com.d.coffeetracker.MyUtils

class CoffeeVM {

    private val coffeeCount = MutableLiveData<Int>()
    private val coffeeDays = MutableLiveData<Int>()
    private val lastDate = MutableLiveData<String>()

    val totalStats = MutableLiveData<Stats>()
    val todayStats = MutableLiveData<Stats>()

    fun addCoffee(type: CoffeeSizes) {
        coffeeCount.postValue(coffeeCount.value?.plus(1))

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
            if (this == lastDate.value) {
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