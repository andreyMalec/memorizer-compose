package com.malec.memorizer.mediator

import com.malec.memorizer.coordinator.main.MainAction
import com.malec.memorizer.coordinator.main.MainCoordinator
import com.malec.memorizer.coordinator.menu.MenuAction
import com.malec.memorizer.coordinator.menu.MenuCoordinator

class MenuAndMainMediator : Mediator<MenuCoordinator, MainCoordinator, MenuAction, MainAction>()