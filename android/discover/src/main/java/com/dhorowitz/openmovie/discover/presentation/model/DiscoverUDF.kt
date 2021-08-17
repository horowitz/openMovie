package com.dhorowitz.openmovie.discover.presentation.model

import com.dhorowitz.openmovie.discover.domain.model.Movie

sealed class DiscoverState {
    data class Content(val items: List<DiscoverViewEntity>) : DiscoverState()
}

sealed class DiscoverAction {
    object Load : DiscoverAction()
}

sealed class DiscoverEvent() {

}



