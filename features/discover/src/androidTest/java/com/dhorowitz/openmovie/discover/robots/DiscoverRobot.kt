package com.dhorowitz.openmovie.discover.robots

import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.dhorowitz.openmovie.discover.R


internal fun discoverRobot(func: DiscoverRobot.() -> Unit) =
    DiscoverRobot().apply { func() }


class DiscoverRobot {
    fun isGridDisplayedCorrectly(expectedItemsCount: Int) {
        assertListNotEmpty(R.id.discoverRecyclerView)
        assertListItemCount(R.id.discoverRecyclerView, expectedItemsCount)
    }
}