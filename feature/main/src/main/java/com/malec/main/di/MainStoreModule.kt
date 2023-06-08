package com.malec.main.di

import com.malec.main.internal.presentation.store.MainStore
import com.malec.main.internal.presentation.store.action.MainAction
import com.malec.main.internal.presentation.store.actionhandler.BackClickActionHandler
import com.malec.main.internal.presentation.store.actionsource.InitActionSource
import com.malec.main.internal.presentation.store.reducer.MainReducer
import com.malec.main.internal.presentation.store.state.MainState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import ru.fabit.storecoroutines.ErrorHandler
import ru.fabit.viewcontroller.ViewControllerComponent
import ru.fabit.viewcontroller.ViewControllerScoped

@Module
@InstallIn(ViewControllerComponent::class)
class MainStoreModule {

    @Provides
    @ViewControllerScoped
    internal fun provideMainStoreStore(
        backClickActionHandler: BackClickActionHandler,
        initActionSource: InitActionSource,
        errorHandler: ErrorHandler,
    ): MainStore {
        return MainStore(
            state = MainState(),
            reducer = MainReducer(),
            errorHandler = errorHandler,
            bootstrapAction = MainAction.Init,
            actionSources = listOf(
                initActionSource
            ),
            bindActionSources = listOf(),
            sideEffects = listOf(
            ),
            actionHandlers = listOf(backClickActionHandler)
        )
    }
}