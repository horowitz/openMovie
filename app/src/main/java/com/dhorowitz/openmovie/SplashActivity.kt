package com.dhorowitz.openmovie

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import com.dhorowitz.openmovie.navigation.navigateTo
import com.gaelmarhic.quadrant.QuadrantConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        findViewById<TextView>(R.id.appNameTextView).startAnimation(R.anim.zoom_in)
        Handler(Looper.getMainLooper()).postDelayed({
            navigateTo(QuadrantConstants.DISCOVER_ACTIVITY)
            finish()
        }, 3000)
    }
}

private fun TextView.startAnimation(@AnimRes animRes: Int) {
    visibility = View.VISIBLE
    startAnimation(AnimationUtils.loadAnimation(context, animRes))
}
