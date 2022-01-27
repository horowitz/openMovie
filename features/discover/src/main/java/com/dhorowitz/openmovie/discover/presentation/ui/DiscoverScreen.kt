package com.dhorowitz.openmovie.discover.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverState.*
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity

@ExperimentalFoundationApi
@Composable
fun DiscoverScreen(state: DiscoverState) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "TopAppBar") }) },
        content = {
            when (state) {
                is Content -> DiscoverMoviesGrid(state.items)
                Error -> TODO()
                Loading -> TODO()
            }
        }
    )
}

@ExperimentalFoundationApi
@Composable
private fun DiscoverMoviesGrid(items: List<DiscoverViewEntity>) {
    LazyVerticalGrid(cells = GridCells.Fixed(3), contentPadding = PaddingValues(8.dp)) {
        items(items) {
            DiscoverGridCell(it)
        }
    }
}

@Composable
fun DiscoverGridCell(viewEntity: DiscoverViewEntity) {
    Image(painter = rememberImagePainter(viewEntity.imageUrl), contentDescription = null, modifier = Modifier.size(200.dp))
}
