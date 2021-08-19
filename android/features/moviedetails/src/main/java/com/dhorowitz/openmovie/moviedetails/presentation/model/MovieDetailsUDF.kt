package com.dhorowitz.openmovie.moviedetails.presentation.model

sealed class MovieDetailsState {
    data class Content(val viewEntity: MovieDetailsViewEntity) : MovieDetailsState()
}

sealed class MovieDetailsAction {
    data class Load(val id: String) : MovieDetailsAction()
}

sealed class MovieDetailsEvent() {
}
