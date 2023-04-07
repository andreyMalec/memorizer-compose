package com.malec.memorizer.coordinator.base

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

class CoordinatorRegistration(
    private vararg val coordinators: Coordinator
) {
    val startRoute = coordinators.first().startScreen.route

    fun register(
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