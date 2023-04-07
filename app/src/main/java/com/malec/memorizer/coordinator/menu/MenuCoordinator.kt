package com.malec.memorizer.coordinator.menu

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.malec.memorizer.coordinator.base.Coordinator
import com.malec.memorizer.mediator.MenuAndMainMediator
import com.malec.menu.dependencies.MenuOutput
import com.malec.menu.view.MenuScreen

class MenuCoordinator(
    private val menuAndMainMediator: MenuAndMainMediator
) : Coordinator(), MenuOutput {
    override val startScreen = MenuScreen(this@MenuCoordinator)

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        modifier: Modifier
    ) {
        with(navGraphBuilder) {
            composable(startScreen.route) {
                startScreen.Content()
            }
        }
    }

    override fun openMainScreen(savedCount: Int?) {
        navController.navigate("${menuAndMainMediator.root}/${savedCount ?: -1}")
    }
}