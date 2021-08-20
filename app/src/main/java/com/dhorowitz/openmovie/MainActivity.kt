package com.dhorowitz.openmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dhorowitz.openmovie.navigation.navigateTo
import com.gaelmarhic.quadrant.QuadrantConstants

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateTo(QuadrantConstants.DISCOVER_ACTIVITY)
    }
}
