package com.dhorowitz.openmovie.discover.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity

@VisibleForTesting
internal const val DISCOVER_MOVIE_GRID_TAG = "DISCOVER_MOVIE_GRID_TAG"
internal const val DISCOVER_MOVIE_GRID_CELL_TAG = "DISCOVER_MOVIE_GRID_CELL_TAG"

@ExperimentalFoundationApi
@Composable
fun DiscoverMoviesGrid(
    items: List<DiscoverViewEntity>,
    onItemClick: (DiscoverViewEntity) -> Unit,
    onLastItemReached: () -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.testTag(DISCOVER_MOVIE_GRID_TAG)
    ) {
        itemsIndexed(items) { index, viewEntity ->
            if (index == items.lastIndex) {
                LaunchedEffect(Unit) { onLastItemReached() }
            }
            Image(
                contentScale = ContentScale.Crop,
                painter = rememberImagePainter(viewEntity.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .clickable { onItemClick(viewEntity) }
                    .testTag(DISCOVER_MOVIE_GRID_CELL_TAG)
            )
        }
    }
}