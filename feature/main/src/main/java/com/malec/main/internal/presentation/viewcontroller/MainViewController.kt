package com.malec.main.internal.presentation.viewcontroller

import com.malec.main.internal.presentation.store.MainStore
import com.malec.main.internal.presentation.store.action.MainAction
import com.malec.main.internal.presentation.store.state.MainState
import com.malec.ui.lifecycle.ViewController
import ru.fabit.viewcontroller.HiltViewController
import javax.inject.Inject

@HiltViewController
class MainViewController @Inject constructor(
    store: MainStore,
) : ViewController<MainState, MainAction>(store) {

    fun onButtonClicked() {
        dispatchAction(MainAction.ButtonClick)
    }

    fun onBackClicked() {
        dispatchAction(MainAction.Back)
    }
}