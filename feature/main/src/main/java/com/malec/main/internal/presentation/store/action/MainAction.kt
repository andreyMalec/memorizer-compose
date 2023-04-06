package com.malec.main.internal.presentation.store.action

sealed interface MainAction {
    object Init : MainAction
    object Back : MainAction
    data class Error(val error: Throwable) : MainAction

    data class ContentLoaded(val text: String, val count: Int) : MainAction

    object ButtonClick : MainAction
}
