package com.dhorowitz.openmovie.discover.presentation.model

sealed class DiscoverState() {

}

sealed class DiscoverAction() {
    object Load : DiscoverAction()
}

sealed class DiscoverEvent() {

}



