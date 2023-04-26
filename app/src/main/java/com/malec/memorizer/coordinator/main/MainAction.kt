package com.malec.memorizer.coordinator.main

import com.malec.memorizer.mediator.Action

sealed interface MainAction: Action {
    data class OpenMainScreen(val counter: Int): MainAction
}