package com.malec.memorizer.coordinator.main

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.Navigator
import com.malec.main.dependencies.MainOutput
import com.malec.main.internal.presentation.view.MainScreen
import com.malec.memorizer.coordinator.base.Coordinator
import com.malec.memorizer.mediator.Action
import com.malec.memorizer.mediator.MenuAndMainMediator
import com.malec.memorizer.navigation.toComposable

class MainCoordinator(private val menuAndMainMediator: MenuAndMainMediator) :
    Coordinator<MainAction, MainScreen.MainScreenParams>(), MainOutput {

    override val startScreen = MainScreen

    override fun receive(action: Action) {
        when (action) {
            is MainAction.OpenMainScreen -> openMainScreen(action.counter)
        }
    }

    override fun registerMediators() {
        menuAndMainMediator.registerAsChildCoordinator(this)
    }

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        modifier: Modifier
    ) {
        with(navGraphBuilder) {
            toComposable(startScreen)
        }
    }

    private fun openMainScreen(counter: Int = -1) {
        navController.navigate(startScreen.newInstance(MainScreen.MainScreenParams(counter)))
    }

    override fun back() {
        navController.popBackStack()
    }
}