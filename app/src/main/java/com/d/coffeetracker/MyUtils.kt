package com.d.coffeetracker

import android.content.Context

object MyUtils {

    private const val sharedPrefs = "coffee_tracker"
    private const val sharedPrefsMode = Context.MODE_PRIVATE
    private const val sharedPrefNotFound = "Not Found"

    fun <T> saveToSharedPrefs(context: Context, key: String, value: T) {
        context.getSharedPreferences(sharedPrefs, sharedPrefsMode).edit().apply {
            when (value) {
                Boolean -> { putBoolean(key, value.toString().toBoolean()) }
                String -> { putString(key, value.toString()) }
                Int -> { putInt(key, value.toString().toInt()) }
                else -> {putString(key, value.toString())}
            }
        }.apply()
    }

    fun getFromSharedPrefs(context: Context, key: String) : String {
        return context.getSharedPreferences(sharedPrefs, sharedPrefsMode).getString(key, sharedPrefNotFound).toString()
    }
}