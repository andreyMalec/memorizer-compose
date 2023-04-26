package com.malec.ui.navigation

import androidx.compose.runtime.Composable

abstract class Screen<T: Screen.ScreenParams> {
    @Composable
    abstract fun Content()

    fun newInstance(params: T): String {
        return "${route}${params.toArgumentsStr()}"
    }

    fun newInstance(): String {
        return route
    }

    abstract class ScreenParams(private vararg val arguments: Any) {
        fun toArgumentsStr(): String {
            var argumentsStr = ""
            arguments.forEach {
                argumentsStr += "/${it}"
            }
            return argumentsStr
        }
    }

    val route: String = "Screen#${this::class.qualifiedName}"

    abstract val arguments: Map<String, Any>
}