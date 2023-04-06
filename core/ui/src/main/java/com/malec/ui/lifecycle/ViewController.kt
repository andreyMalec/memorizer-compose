package com.malec.ui.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.model.ScreenModel
import com.github.terrakok.modo.model.ScreenModelStore
import kotlinx.coroutines.flow.Flow
import ru.fabit.storecoroutines.Store
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class ViewController<State, Action>(
    protected val store: Store<State, Action>
) : ViewModel(), ScreenModel {
    private val state: Flow<State> = store.state

    init {
        store.start()
    }

    protected fun dispatchAction(action: Action) {
        store.dispatchAction(action)
    }

    override fun onCleared() {
        store.dispose()
    }

    override fun onDispose() {
        onCleared()
    }

    @Composable
    fun renderState(
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        context: CoroutineContext = EmptyCoroutineContext
    ): androidx.compose.runtime.State<State> = state.collectAsStateWithLifecycle(
        initialValue = store.currentState,
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = minActiveState,
        context = context
    )
}

@Composable
inline fun <State, Action, reified T : ViewController<State, Action>> viewController() =
    hiltViewModel<T>()
