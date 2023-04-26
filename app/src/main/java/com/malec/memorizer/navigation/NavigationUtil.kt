package com.malec.memorizer.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.malec.ui.navigation.Screen

fun NavGraphBuilder.toComposable(screen: Screen<out Screen.ScreenParams>) {
    var argumentsStr = ""
    val navArguments = mutableListOf<NamedNavArgument>()
    screen.arguments.forEach { (key, value) ->
        argumentsStr += "/{$key}"
        buildNavType(value)?.let {
            navArguments.add(navArgument(key) {
                type = it
            })
        }
    }
    var route = screen.route
    if (argumentsStr.isNotEmpty()) route += argumentsStr
    composable(
        route = route,
        arguments = navArguments
    ) {
        screen.Content()
    }
}

private fun <T> buildNavType(type: T): NavType<*>? {
    return when (type) {
        is Int -> NavType.IntType
        is String -> NavType.StringType
        is Long -> NavType.LongType
        is Float -> NavType.FloatType
        is Boolean -> NavType.BoolType
        else -> null
    }
}
