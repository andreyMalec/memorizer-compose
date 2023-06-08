package com.malec.ui.navigation

import androidx.compose.runtime.Composable

abstract class Screen<T : ScreenParams> {
    @Composable
    abstract fun Content()

    fun newInstance(params: T): String {
        return "${route}${params.toArgumentsStr()}"
    }

    fun newInstance(): String {
        return route
    }

    val route: String = "Screen#${this::class.qualifiedName}"
}