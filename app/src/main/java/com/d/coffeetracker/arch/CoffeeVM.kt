package com.d.coffeetracker.arch

import androidx.lifecycle.MutableLiveData
import com.d.coffeetracker.MyUtils
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@OptIn(InternalCoroutinesApi::class)
class CoffeeVM {

    private val coffeeCount = MutableLiveData<Int>()
    private val coffeeDays = MutableLiveData<Int>()
    private val lastDate = MutableLiveData<String>()

    val totalStats = MutableLiveData<Stats>()
    val todayStats = MutableLiveData<Stats>()

    fun addCoffee(type: CoffeeSizes) {
        synchronized(this) {
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
    }

    fun saveTodayDate() {
        synchronized(this) {
            MyUtils.getTodayDate().apply {
                if (this == lastDate.value) {
                    lastDate.postValue(this)
                }
            }
        }
    }

    fun getTodayStats(): Stats? {
        return todayStats.value
    }

    fun saveToTodayStats() {
        synchronized(this) {

        }
    }

    fun saveToTotalStats() {
        synchronized(this) {

        }
    }

    enum class CoffeeSizes { SMALL, MEDIUM, GRANDE }
    enum class CoffeeSizesML(val ml: Int) {
        SMALL(55),
        MEDIUM(75),
        GRANDE(105)
    }
}