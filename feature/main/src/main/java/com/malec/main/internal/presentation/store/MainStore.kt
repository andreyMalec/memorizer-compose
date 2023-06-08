package com.malec.main.internal.presentation.store

import com.malec.main.internal.presentation.store.action.MainAction
import com.malec.main.internal.presentation.store.reducer.MainReducer
import com.malec.main.internal.presentation.store.state.MainEvent
import com.malec.main.internal.presentation.store.state.MainState
import ru.fabit.storecoroutines.ActionHandler
import ru.fabit.storecoroutines.ActionSource
import ru.fabit.storecoroutines.BindActionSource
import ru.fabit.storecoroutines.ErrorHandler
import ru.fabit.storecoroutines.EventsStore
import ru.fabit.storecoroutines.SideEffect

class MainStore(
    state: MainState,
    reducer: MainReducer,
    errorHandler: ErrorHandler,
    bootstrapAction: MainAction,
    actionSources: List<ActionSource<MainAction>>,
    bindActionSources: List<BindActionSource<MainState, MainAction>>,
    sideEffects: List<SideEffect<MainState, MainAction>>,
    actionHandlers: List<ActionHandler<MainState, MainAction>>,
) : EventsStore<MainState, MainAction, MainEvent>(
    startState = state,
    reducer = reducer,
    errorHandler = errorHandler,
    bootstrapAction = bootstrapAction,
    sideEffects = sideEffects,
    bindActionSources = bindActionSources,
    actionSources = actionSources,
    actionHandlers = actionHandlers
)