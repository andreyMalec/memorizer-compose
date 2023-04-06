package com.malec.main.internal.presentation.store.reducer

import com.malec.main.internal.presentation.store.action.MainAction
import com.malec.main.internal.presentation.store.state.MainEvent
import com.malec.main.internal.presentation.store.state.MainState
import ru.fabit.storecoroutines.EventsReducer

class MainReducer : EventsReducer<MainState, MainAction> {
    override fun reduce(
        state: MainState,
        action: MainAction
    ): MainState {
        return when (action) {
            is MainAction.ContentLoaded -> state.copy(
                text = action.text,
                clickCount = action.count
            )
            is MainAction.Error -> state.copy().apply {
                action.error.message?.let {
                    addEvent(MainEvent.Error(it))
                }
            }
            else -> state.copy()
        }
    }

    override fun copy(state: MainState) = state.copy()
}