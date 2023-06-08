package com.malec.main.internal.presentation.store.actionsource

import androidx.lifecycle.SavedStateHandle
import com.malec.domain.QuestRepo
import com.malec.main.internal.presentation.store.action.MainAction
import kotlinx.coroutines.flow.map
import ru.fabit.storecoroutines.ActionSource
import javax.inject.Inject

class InitActionSource @Inject constructor(
    savedStateHandle: SavedStateHandle,
    questRepo: QuestRepo
) : ActionSource<MainAction>(
    source = {
//            val params = construct<MainScreen.MainScreenParams>(savedStateHandle)
        questRepo.quests().map {
            MainAction.ContentLoaded(it)
        }
    },
    error = { MainAction.Error(it) }
)