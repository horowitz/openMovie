package com.dhorowitz.openmovie.moviedetails.presentation.model

sealed class MovieDetailsState {
    object Loading: MovieDetailsState()
    object Error: MovieDetailsState()
    data class Content(val viewEntity: MovieDetailsViewEntity) : MovieDetailsState()
}

sealed class MovieDetailsAction {
    data class Load(val id: String) : MovieDetailsAction()
    data class HomepageButtonClicked(val url: String) : MovieDetailsAction()
    data class ImdbButtonClicked(val url: String) : MovieDetailsAction()
}

sealed class MovieDetailsEvent {
    data class NavigateToBrowser(val url: String): MovieDetailsEvent()
}
