package com.malec.main.dependencies

import androidx.navigation.NavGraphBuilder

interface MainInput {
    fun NavGraphBuilder.openMainScreen()

    companion object {
        const val key = "savedCount"
    }
}