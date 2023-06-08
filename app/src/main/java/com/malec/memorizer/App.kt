package com.malec.memorizer

import android.app.Application
import com.malec.memorizer.coordinator.base.CoordinatorRegistration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var coordinatorRegistration: CoordinatorRegistration

    override fun onCreate() {
        super.onCreate()
        coordinatorRegistration.registerMediators()
    }
}