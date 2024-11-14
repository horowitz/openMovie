package com.dhorowitz.openmovie.discover.presentation.model

sealed class DiscoverState() {
    data object Loading: DiscoverState()
    data class Error(val error: Throwable): DiscoverState()
    data class Content(val items: List<DiscoverViewEntity>) : DiscoverState()
}

sealed class DiscoverAction {
    data object Load : DiscoverAction()
    data class ItemClicked(val item: DiscoverViewEntity) : DiscoverAction()
}

sealed class DiscoverEvent {
    data class NavigateToMovieDetails(val id: String, val className: String): DiscoverEvent()
}



