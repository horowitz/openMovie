package com.dhorowitz.openmovie.moviedetails.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDTO(
   val id: String,
   val title: String,
   @SerialName("poster_path")
   val posterPath: String,
   @SerialName("backdrop_path")
   val backdropPath: String,
   val tagline: String,
   val overview: String,
   val homepage: String,
   @SerialName("vote_average")
   val voteAverage: Double,
   val runtime: Int,
)