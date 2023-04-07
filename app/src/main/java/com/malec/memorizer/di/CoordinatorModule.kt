package com.malec.memorizer.di

import com.malec.main.dependencies.MainInput
import com.malec.main.dependencies.MainOutput
import com.malec.memorizer.coordinator.base.CoordinatorRegistration
import com.malec.memorizer.coordinator.main.MainCoordinator
import com.malec.memorizer.coordinator.menu.MenuCoordinator
import com.malec.memorizer.mediator.MenuAndMainMediator
import com.malec.menu.dependencies.MenuOutput
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoordinatorModule {

    @Provides
    @Singleton
    internal fun provideCoordinatorRegistration(
        mainCoordinator: MainCoordinator,
        menuCoordinator: MenuCoordinator
    ): CoordinatorRegistration = CoordinatorRegistration(
        menuCoordinator,
        mainCoordinator
    )

    @Provides
    @Singleton
    internal fun provideMainCoordinator(): MainCoordinator = MainCoordinator()

    @Provides
    @Singleton
    internal fun provideMenuCoordinator(
        menuAndMainMediator: MenuAndMainMediator
    ): MenuCoordinator = MenuCoordinator(
        menuAndMainMediator
    )

    @Provides
    @Singleton
    internal fun provideMenuOutput(
        coordinator: MenuCoordinator
    ): MenuOutput = coordinator

    @Provides
    @Singleton
    internal fun provideMainOutput(
        coordinator: MainCoordinator
    ): MainOutput = coordinator

    @Provides
    @Singleton
    internal fun provideMainInput(
        coordinator: MainCoordinator
    ): MainInput = coordinator
}