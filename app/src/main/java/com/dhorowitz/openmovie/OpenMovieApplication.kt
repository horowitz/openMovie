package com.dhorowitz.openmovie

import android.app.Application
import android.content.Intent
import com.dhorowitz.openmovie.BuildConfig.*
import com.dhorowitz.openmovie.discover.presentation.DiscoverActivity
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class OpenMovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        when {
            DEBUG -> Timber.plant(DebugTree())
        }
    }
}
