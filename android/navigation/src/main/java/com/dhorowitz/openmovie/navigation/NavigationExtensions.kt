package com.dhorowitz.openmovie.navigation

import android.app.Activity
import android.content.Intent

fun Activity.navigateTo(className: String) {
    val intent = Intent()
    intent.setClassName(this, className)
    startActivity(intent)
}