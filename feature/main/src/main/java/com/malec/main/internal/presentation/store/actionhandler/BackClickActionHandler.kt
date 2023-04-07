package com.malec.main.internal.presentation.store.actionhandler

import com.malec.main.dependencies.MainOutput
import com.malec.main.internal.presentation.store.action.MainAction
import com.malec.main.internal.presentation.store.state.MainState
import ru.fabit.storecoroutines.ActionHandler
import javax.inject.Inject

class BackClickActionHandler @Inject constructor(
    output: MainOutput
) : ActionHandler<MainState, MainAction>(
    requirement = { action -> action is MainAction.Back },
    handler = { _, _ ->
        output.back()
    }
)