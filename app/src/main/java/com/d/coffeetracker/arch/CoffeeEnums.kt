package com.d.coffeetracker.arch

enum class CoffeeSizes { SMALL, MEDIUM, GRANDE }
enum class CoffeeSizesML(val ml: Int) {
    SMALL(55),
    MEDIUM(75),
    GRANDE(105)
}

enum class CoffeeStats { TOTAL, SMALL, MEDIUM, GRANDE}