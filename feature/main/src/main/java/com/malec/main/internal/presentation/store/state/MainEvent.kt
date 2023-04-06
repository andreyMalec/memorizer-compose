package com.malec.main.internal.presentation.store.state

sealed interface MainEvent {
    data class Error(val message: String) : MainEvent
}
