package com.dhorowitz.openmovie.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.gaelmarhic.quadrant.QuadrantConstants

fun Activity.navigateTo(className: String, extras: Bundle? = null) {
    val intent = Intent()
    extras?.let { intent.putExtras(it) }
    intent.setClassName(this, className)
    startActivity(intent)
}

fun Activity.navigateToMovieDetails(id: String) {
    navigateTo(
        QuadrantConstants.MOVIE_DETAILS_ACTIVITY,
        Bundle().apply { putString("id", id) }
    )
}
