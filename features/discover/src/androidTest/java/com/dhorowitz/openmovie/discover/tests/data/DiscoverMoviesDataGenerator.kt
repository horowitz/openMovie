package com.dhorowitz.openmovie.discover.tests.data

import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity

internal fun discoverViewEntity(
    id: String = "id",
    title: String = "id",
    image: String = "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
) = DiscoverViewEntity(id, title, image)