package com.dhorowitz.openmovie.discover.presentation.mapper

import com.dhorowitz.openmovie.discover.domain.model.Movie
import com.dhorowitz.openmovie.discover.presentation.model.DiscoverViewEntity

fun Movie.toDiscoverViewEntity(): DiscoverViewEntity =
    DiscoverViewEntity(id = id, title = title, imageUrl = image)