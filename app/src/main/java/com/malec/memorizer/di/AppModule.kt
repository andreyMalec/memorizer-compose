package com.malec.memorizer.di

import android.util.Log
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
}