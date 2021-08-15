package com.dhorowitz.openmovie.discover.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dhorowitz.openmovie.R
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverAction.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverActivity : AppCompatActivity() {

    private val viewModel: DiscoverViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        viewModel.handle(Load)
    }
}
