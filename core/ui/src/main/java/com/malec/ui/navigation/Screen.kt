package com.malec.ui.navigation

import androidx.compose.runtime.Composable

abstract class Screen {
    @Composable
    abstract fun Content()

    val route: String = "Screen#${this::class.qualifiedName}"
}