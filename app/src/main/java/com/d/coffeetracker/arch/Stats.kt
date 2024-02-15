package com.d.coffeetracker.arch

class Stats(
    val day: String,
    val date: String,
    val smallCups: ArrayList<Cup>,
    val mediumCups: ArrayList<Cup>,
    val grandeCups: ArrayList<Cup>
) {
    fun addCup(small: Boolean? = null, medium: Boolean? = null, grande: Boolean? = null) {
        small?.let { smallCups.add(Cup(CoffeeSizes.SMALL, CoffeeSizesML.SMALL)) }
        medium?.let { mediumCups.add(Cup(CoffeeSizes.MEDIUM, CoffeeSizesML.MEDIUM)) }
        grande?.let { grandeCups.add(Cup(CoffeeSizes.GRANDE, CoffeeSizesML.GRANDE)) }
    }
}