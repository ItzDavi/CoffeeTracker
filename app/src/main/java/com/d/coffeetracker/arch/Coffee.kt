package com.d.coffeetracker.arch

import androidx.lifecycle.MutableLiveData
import java.util.Date

class Coffee {

    val coffeeCount = MutableLiveData<Int>()
    val coffeeDays = MutableLiveData<Int>()
    val lastDate = MutableLiveData<Date>()

    val totalStats = MutableLiveData<Stats>()
    val todayStats = MutableLiveData<Stats>()

    fun addCoffee() {

    }

    fun getTodayStats() {

    }

    fun saveToTodayStats() {

    }

    fun saveToTotalStats() {

    }
}