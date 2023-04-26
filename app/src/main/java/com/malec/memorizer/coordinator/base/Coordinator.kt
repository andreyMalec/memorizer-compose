package com.malec.memorizer.coordinator.base

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.malec.memorizer.mediator.Action
import com.malec.ui.navigation.Screen

abstract class Coordinator<out T : Action, P : Screen.ScreenParams>  {

    abstract fun registerMediators()
    open fun receive(action: Action) {
        throw Exception("I can't receive action")
    }

    protected lateinit var navController: NavController

    abstract val startScreen: Screen<P>

    abstract fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        modifier: Modifier = Modifier
    )

    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier = Modifier
    ) {
        this.navController = navController
        registerGraph(navGraphBuilder, modifier)
    }
}