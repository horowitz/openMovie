package com.dhorowitz.openmovie.discover.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    val page: Int,
    val results: List<T>
)
