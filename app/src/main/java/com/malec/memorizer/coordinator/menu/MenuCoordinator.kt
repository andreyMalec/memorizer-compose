package com.malec.memorizer.coordinator.menu

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.malec.memorizer.coordinator.base.Coordinator
import com.malec.memorizer.coordinator.main.MainAction
import com.malec.memorizer.mediator.MenuAndMainMediator
import com.malec.memorizer.navigation.toComposable
import com.malec.menu.dependencies.MenuOutput
import com.malec.menu.view.MenuScreen
import com.malec.ui.navigation.Screen

class MenuCoordinator(
    private val menuAndMainMediator: MenuAndMainMediator
) : Coordinator<MenuAction, Screen.ScreenParams>(), MenuOutput {
    override fun registerMediators() {
        menuAndMainMediator.registerAsParentCoordinator(this)
    }

    override val startScreen = MenuScreen(this@MenuCoordinator)

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        modifier: Modifier
    ) {
        with(navGraphBuilder) {
            toComposable(startScreen)
        }
    }

    override fun openMainScreen(savedCount: Int?) {
        menuAndMainMediator.sendToChild(MainAction.OpenMainScreen(savedCount ?: -1))
    }
}