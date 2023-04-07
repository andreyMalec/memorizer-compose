package com.malec.memorizer.mediator

import androidx.navigation.NamedNavArgument
import com.malec.memorizer.coordinator.main.MainCoordinator
import com.malec.memorizer.coordinator.menu.MenuCoordinator
import javax.inject.Inject

class MenuAndMainMediator @Inject constructor(
    output: MainCoordinator,
) : Mediator<MenuCoordinator, MainCoordinator> {
    override val root: String = output.startScreen.route
    override val arguments: List<NamedNavArgument> = listOf()
}