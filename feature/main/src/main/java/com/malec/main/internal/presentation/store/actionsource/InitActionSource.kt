package com.malec.main.internal.presentation.store.actionsource

import androidx.lifecycle.SavedStateHandle
import com.malec.main.internal.presentation.store.action.MainAction
import com.malec.main.internal.presentation.view.MainScreen
import com.malec.ui.navigation.ScreenParams.Companion.construct
import kotlinx.coroutines.flow.flow
import ru.fabit.storecoroutines.ActionSource
import javax.inject.Inject

class InitActionSource @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ActionSource<MainAction>(
    source = {
        flow {
            val params = construct<MainScreen.MainScreenParams>(savedStateHandle)
            val count = params.count
            println("______ $params")
            if (count > 0)
                emit(MainAction.ContentLoaded("Continue from $count", count))
        }
    },
    error = { MainAction.Error(it) }
)