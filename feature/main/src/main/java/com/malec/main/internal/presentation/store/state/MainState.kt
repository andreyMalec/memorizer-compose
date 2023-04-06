package com.malec.main.internal.presentation.store.state

import ru.fabit.storecoroutines.EventsState

data class MainState(
    val text: String = "Start text",
    val clickCount: Int = 0
) : EventsState<MainEvent>()
