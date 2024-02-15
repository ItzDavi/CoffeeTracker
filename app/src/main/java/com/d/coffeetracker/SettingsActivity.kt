package com.d.coffeetracker

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d.coffeetracker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUI()

        setListeners()
    }

    private fun loadUI() {
        MyUtils.setStatusBarColor(this, window, R.color.background)
    }

    private fun setListeners() {
        with (binding) {
            back.setOnClickListener {
                finish()
                if (Build.VERSION.SDK_INT < 34) {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            }
        }
    }
}