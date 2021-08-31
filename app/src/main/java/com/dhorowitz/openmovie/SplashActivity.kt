package com.dhorowitz.openmovie

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dhorowitz.openmovie.navigation.navigateTo
import com.gaelmarhic.quadrant.QuadrantConstants

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            navigateTo(QuadrantConstants.DISCOVER_ACTIVITY)
            finish()
        }, 3000)
    }
}
