package com.d.coffeetracker

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.d.coffeetracker.arch.CoffeeVM
import com.d.coffeetracker.databinding.ActivitySettingsBinding
import com.d.coffeetracker.utils.MyUtils

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: CoffeeVM by viewModels()

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
        val context = this

        with (binding) {
            back.setOnClickListener {
                finish()
                if (Build.VERSION.SDK_INT < 34) {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            }

            resetStats.setOnClickListener {
                MyUtils.resetSharedPrefs(context)
                viewModel.firstSetup(context)
                finish()
                if (Build.VERSION.SDK_INT < 34) {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            }

            iconsAttributions.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/")))
            }
        }
    }
}