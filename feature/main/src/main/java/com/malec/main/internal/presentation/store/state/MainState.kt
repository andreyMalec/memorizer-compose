package com.malec.main.internal.presentation.store.state

import com.malec.domain.entity.Quest
import ru.fabit.storecoroutines.EventsState

data class MainState(
    val items: List<Quest> = listOf(),
    val clickCount: Int = 0
) : EventsState<MainEvent>()
