package com.dhorowitz.openmovie.test

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class SingleViewActivity : AppCompatActivity() {

    val root by lazy { findViewById<FrameLayout>(R.id.root) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.single_view_activity)
    }
}