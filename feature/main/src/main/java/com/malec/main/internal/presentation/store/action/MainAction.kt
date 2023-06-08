package com.malec.main.internal.presentation.store.action

import com.malec.domain.entity.Quest

sealed interface MainAction {
    object Init : MainAction
    object Back : MainAction
    data class Error(val error: Throwable) : MainAction

    data class ContentLoaded(val items: List<Quest>) : MainAction
}
