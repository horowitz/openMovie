package com.dhorowitz.openmovie.discover.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    val id: String,
    val overview: String,
    val title: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("poster_path")
    val posterPath: String
)
