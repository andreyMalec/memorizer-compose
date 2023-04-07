package com.malec.main.internal.presentation.store.actionsource

import androidx.lifecycle.SavedStateHandle
import com.malec.main.dependencies.MainInput.Companion.key
import com.malec.main.internal.presentation.store.action.MainAction
import kotlinx.coroutines.flow.flow
import ru.fabit.storecoroutines.ActionSource
import javax.inject.Inject

class InitActionSource @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ActionSource<MainAction>(
    source = {
        flow {
            val count = savedStateHandle.get<Int>(key) ?: throw IllegalArgumentException()
            if (count > 0)
                emit(MainAction.ContentLoaded("Continue from $count", count))
        }
    },
    error = { MainAction.Error(it) }
)