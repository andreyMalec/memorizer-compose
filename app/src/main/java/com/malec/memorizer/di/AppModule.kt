package com.malec.memorizer.di

import android.util.Log
import com.malec.domain.QuestRepo
import com.malec.domain.entity.OnQuestComplete
import com.malec.main.QuestRepoImpl
import com.malec.ui.MathMultiplyQuestAdapter
import com.malec.ui.MathSumQuestAdapter
import com.malec.ui.QuestAdapterManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fabit.storecoroutines.ErrorHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    internal fun provideErrorHandler(): ErrorHandler = object : ErrorHandler {
        override fun handle(t: Throwable) {
            Log.e("ErrorHandler", "handle: ", t)
        }
    }

    @Provides
    @Singleton
    internal fun provideQuestRepoImpl(): QuestRepoImpl = QuestRepoImpl()

    @Provides
    @Singleton
    internal fun provideQuestRepo(
        questRepoImpl: QuestRepoImpl
    ): QuestRepo = questRepoImpl

    @Provides
    @Singleton
    internal fun provideOnQuestComplete(
        questRepo: QuestRepoImpl
    ): OnQuestComplete = questRepo

    @Provides
    @Singleton
    internal fun provideQuestAdapterManager(
        listener: OnQuestComplete
    ): QuestAdapterManager {
        val manager = QuestAdapterManager(listener)
        manager.init(MathSumQuestAdapter(), MathMultiplyQuestAdapter())
        return manager
    }
}