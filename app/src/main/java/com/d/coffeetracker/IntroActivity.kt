package com.d.coffeetracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class IntroActivity : AppIntro2() {

    private var firstFragmentTag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //if (MyUtils.getFromSharedPrefs(this, Constants.SP_FIRST_SETUP) == MyUtils.SharedPrefNotFound)

        setupAndStart()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupAndStart() {

        val violet = getColor(R.color.violet)
        val fakeWhite = getColor(R.color.fake_white)

        val drawable = (R.drawable.coffeeintro)
        val drawable2 = (R.drawable.privacycare)

        val poppinsBold = R.font.poppinsbold
        val poppinsReg = R.font.poppinsregular

        val fragments = arrayListOf(
            AppIntroFragment.newInstance(
                title = "Coffee Tracker",
                description = "Your private, offline, coffee tracker.",
                backgroundColor = getColor(R.color.fake_white),
                titleColor = violet,
                titleTypefaceFontRes = poppinsBold,
                descriptionColor = violet,
                descriptionTypefaceFontRes = poppinsReg,
                imageDrawable = drawable
            ).apply {
                firstFragmentTag = tag.toString()
            },
            AppIntroFragment.newInstance(
                title = "Private. Offline.\nOpen source.",
                description = "We care about your privacy.\nOur tracker doesn't track you.",
                backgroundColor = getColor(R.color.background),
                titleColor = fakeWhite,
                titleTypefaceFontRes = poppinsBold,
                descriptionColor = fakeWhite,
                descriptionTypefaceFontRes = poppinsReg,
                imageDrawable = drawable2
            )
        )

        for (frag in fragments) {
            addSlide(frag)
        }

        //isColorTransitionsEnabled = true
        isIndicatorEnabled = true
        isSystemBackButtonLocked = true
        isSkipButtonEnabled = false

        setTransformer(AppIntroPageTransformerType.Parallax(
            titleParallaxFactor = 1.0,
            imageParallaxFactor = -1.0,
            descriptionParallaxFactor = 2.0
        ))

        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.violet),
            unselectedIndicatorColor = getColor(R.color.gray)
        )

        setImmersiveMode()
        showStatusBar(false)
    }
}