package com.d.coffeetracker.arch

import com.d.coffeetracker.arch.CoffeeVM.CoffeeSizes
import com.d.coffeetracker.arch.CoffeeVM.CoffeeSizesML


class Stats(
    val day: String,
    val date: String,
    private val smallCups: ArrayList<Cup>,
    private val mediumCups: ArrayList<Cup>,
    private val grandeCups: ArrayList<Cup>
) {
    fun addCup(small: Boolean? = null, medium: Boolean? = null, grande: Boolean? = null) {
        small?.let { smallCups.add(Cup(CoffeeSizes.SMALL, CoffeeSizesML.SMALL)) }
        medium?.let { mediumCups.add(Cup(CoffeeSizes.MEDIUM, CoffeeSizesML.MEDIUM)) }
        grande?.let { grandeCups.add(Cup(CoffeeSizes.GRANDE, CoffeeSizesML.GRANDE)) }
    }
}