package com.malec.memorizer.coordinator.base

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.malec.memorizer.mediator.Action
import com.malec.ui.navigation.ScreenParams

class CoordinatorRegistration(
    private vararg val coordinators: Coordinator<Action, out ScreenParams>
) {
    val startRoute = coordinators.first().startScreen.route

    fun registerMediators() {
        coordinators.forEach {
            it.registerMediators()
        }
    }

    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
    ) {
        coordinators.forEach {
            it.registerGraph(
                navGraphBuilder,
                navController
            )
        }
    }
}