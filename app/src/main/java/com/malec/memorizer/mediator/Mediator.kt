package com.malec.memorizer.mediator

import androidx.navigation.NamedNavArgument
import com.malec.memorizer.coordinator.base.Coordinator
import com.malec.ui.navigation.Screen

interface Action
abstract class Mediator<C1 : Coordinator<Action, out Screen.ScreenParams>, C2 : Coordinator<Action, out Screen.ScreenParams>, A1 : Action, A2 : Action> {

    private var parentCoordinator: C1? = null
    private var childCoordinator: C2? = null

    fun registerCoordinators(
        parentCoordinator: C1,
        childCoordinator: C2
    ) {
        this.parentCoordinator = parentCoordinator
        this.childCoordinator = childCoordinator
    }

    fun registerAsParentCoordinator(
        parentCoordinator: C1
    ) {
        this.parentCoordinator = parentCoordinator
    }

    fun registerAsChildCoordinator(
        childCoordinator: C2
    ) {
        this.childCoordinator = childCoordinator
    }

    fun sendToParent(action: A1) {
        parentCoordinator?.receive(action)
            ?: throw Exception("You need call registerCoordinators method before")
    }

    fun sendToChild(action: A2) {
        childCoordinator?.receive(action)
            ?: throw Exception("You need call registerCoordinators method before")
    }
}