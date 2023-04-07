package com.malec.memorizer.coordinator.main

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.malec.main.dependencies.MainInput
import com.malec.main.dependencies.MainInput.Companion.key
import com.malec.main.dependencies.MainOutput
import com.malec.main.internal.presentation.view.MainScreen
import com.malec.memorizer.coordinator.base.Coordinator

class MainCoordinator : Coordinator(), MainOutput, MainInput {
    override val startScreen = MainScreen()

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        modifier: Modifier
    ) {
        with(navGraphBuilder) {
            openMainScreen()
        }
    }

    override fun back() {
        navController.popBackStack()
    }

    override fun NavGraphBuilder.openMainScreen() {
        composable(
            route = "${startScreen.route}/{$key}",
            arguments = listOf(
                navArgument(key) {
                    type = NavType.IntType
                }
            ),
        ) {
            startScreen.Content()
        }
    }
}