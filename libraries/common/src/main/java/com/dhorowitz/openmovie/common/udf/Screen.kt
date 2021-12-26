package com.dhorowitz.openmovie.common.udf

interface Screen<State, Event> {
    fun handleState(state: State)
    fun handleEvent(event: Event)
}