package com.d.coffeetracker.utils

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.delay
import java.io.Serializable
import java.util.Calendar

object MyUtils {

    private const val sharedPrefs = "coffee_tracker"
    private const val sharedPrefsMode = Context.MODE_PRIVATE
    const val SharedPrefNotFound = "No record"

    fun setStatusBarColor(context: Context, window: Window, color: Int, light: Boolean? = null) {
        window.statusBarColor = context.getColor(color)

        light?.let {
            WindowInsetsControllerCompat(window, window.decorView).apply {
                isAppearanceLightStatusBars = light
            }
        }
    }

    fun <T> saveToSharedPrefs(context: Context, key: String, value: T) {
        context.getSharedPreferences(sharedPrefs, sharedPrefsMode).edit().apply {
            when (value) {
                Boolean -> { putBoolean(key, value.toString().toBoolean()) }
                String  -> { putString(key, value.toString()) }
                Int     -> { putInt(key, value.toString().toInt()) }
                else    -> { putString(key, value.toString()) }
            }
        }.apply()
    }

    inline fun <reified T : Serializable> Bundle.customGetSerializable(key: String): T? {
        @Suppress("DEPRECATION")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializable(key, T::class.java)
        } else {
            getSerializable(key) as? T
        }
    }

    fun getFromSharedPrefs(context: Context, key: String) : String {
        return context.getSharedPreferences(sharedPrefs, sharedPrefsMode).getString(key, SharedPrefNotFound).toString()
    }

    fun resetSharedPrefs(context: Context) {
        context.getSharedPreferences(sharedPrefs, sharedPrefsMode).edit().clear().apply()
    }

    fun getTodayDate() : String {
        return "${Calendar.DAY_OF_MONTH}/${Calendar.MONTH}/${Calendar.YEAR}"
    }

    suspend inline fun View.autoAnimate(animID: Int, fillBefore: Boolean? = null, fillAfter: Boolean? = null, delayBefore: Long? = null, delayAfter: Long? = null) {
        val anim = AnimationUtils.loadAnimation(context, animID).apply {
            fillBefore?.let {
                setFillBefore(fillBefore)
                setFillAfter(!fillBefore)
            }
            fillAfter?.let {
                setFillBefore(!fillAfter)
                setFillAfter(fillAfter)
            }
        }

        when (this.visibility) {
            View.VISIBLE -> {
                this.visibility = View.INVISIBLE
            }
            else -> {
                this.visibility = View.VISIBLE
            }
        }

        if (delayBefore != null || delayAfter != null) {

            delay(delayBefore ?: 0L)
            this.startAnimation(anim)
            delay(delayAfter ?: 0L)

            // delayBefore = null or delayAfter = null
        } else {
            this.startAnimation(anim)
        }
    }
}