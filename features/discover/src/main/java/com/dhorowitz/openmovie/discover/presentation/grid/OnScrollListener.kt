package com.dhorowitz.openmovie.discover.presentation.grid

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnScrollListener(val onEndReached: () -> Unit) : RecyclerView.OnScrollListener() {
    val visibleThreshold = 10
    var loading = true
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var previousTotal = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy);

        val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        visibleItemCount = recyclerView.childCount
        totalItemCount = gridLayoutManager.itemCount
        firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && hasMoreItemsThanThreshold() && hasReachedEnd()) {
            onEndReached()

            loading = true;
        }
    }

    private fun hasMoreItemsThanThreshold() = visibleThreshold < totalItemCount

    private fun hasReachedEnd() =
        (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)
}