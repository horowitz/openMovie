package com.dhorowitz.openmovie.discover.presentation.model

sealed class DiscoverState {
    object Loading: DiscoverState()
    data class Content(val items: List<DiscoverViewEntity>) : DiscoverState()
}

sealed class DiscoverAction {
    object Load : DiscoverAction()
    data class ItemClicked(val item: DiscoverViewEntity) : DiscoverAction()
}

sealed class DiscoverEvent() {
    data class NavigateToMovieDetails(val id: String, val className: String): DiscoverEvent()
}



