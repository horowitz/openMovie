package com.dhorowitz.openmovie.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    val page: Int,
    val results: List<T>
)
