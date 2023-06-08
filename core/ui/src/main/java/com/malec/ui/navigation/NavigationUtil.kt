package com.malec.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlin.reflect.KType
import kotlin.reflect.typeOf

inline fun <reified Params : ScreenParams> NavGraphBuilder.toComposable(screen: Screen<Params>) {
    var argumentsStr = ""
    val navArguments = mutableListOf<NamedNavArgument>()
    arguments<Params>().forEach { (key, value) ->
        argumentsStr += "/{$key}"
        value?.let {
            navArguments.add(navArgument(key) {
                type = it
            })
        }
    }
    val route = if (argumentsStr.isNotEmpty())
        screen.route + argumentsStr
    else
        screen.route
    composable(
        route = route,
        arguments = navArguments
    ) {
        screen.Content()
    }
}

inline fun <reified Params : ScreenParams> arguments(): Map<String, NavType<*>?> {
    val constructor = Params::class.constructors.firstOrNull() ?: return mapOf()
    return constructor.parameters.associate {
        (it.name ?: "") to it.type.navType()
    }
}

fun KType.navType() = when (this) {
    typeOf<Int>() -> NavType.IntType
    typeOf<Long>() -> NavType.LongType
    typeOf<Float>() -> NavType.FloatType
    typeOf<Boolean>() -> NavType.BoolType
    typeOf<String>() -> NavType.StringType
    else -> null
}