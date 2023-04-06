package com.malec.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import ru.fabit.storecoroutines.ErrorHandler
import ru.fabit.viewcontroller.ViewControllerComponent
import ru.fabit.viewcontroller.ViewControllerScoped
import com.malec.main.internal.presentation.store.MainStore
import com.malec.main.internal.presentation.store.action.MainAction
import com.malec.main.internal.presentation.store.actionhandler.BackClickActionHandler
import com.malec.main.internal.presentation.store.reducer.MainReducer
import com.malec.main.internal.presentation.store.sideeffect.GetContentSideEffect
import com.malec.main.internal.presentation.store.state.MainState
import com.malec.main.internal.presentation.viewcontroller.MainViewController

@Module
@InstallIn(ViewControllerComponent::class)
class MainStoreModule {

    @Provides
    @ViewControllerScoped
    fun provideStore(
        backClickActionHandler: BackClickActionHandler,
        getContentSideEffect: GetContentSideEffect,
        errorHandler: ErrorHandler,
    ): MainStore {
        return MainStore(
            state = MainState(),
            reducer = MainReducer(),
            errorHandler = errorHandler,
            bootstrapAction = MainAction.Init,
            actionSources = listOf(),
            bindActionSources = listOf(),
            sideEffects = listOf(
                getContentSideEffect
            ),
            actionHandlers = listOf(backClickActionHandler)
        )
    }
}