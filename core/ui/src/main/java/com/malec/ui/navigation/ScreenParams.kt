package com.malec.ui.navigation

import androidx.lifecycle.SavedStateHandle

abstract class ScreenParams(private vararg val arguments: Any?) {
    fun toArgumentsStr(): String {
        var argumentsStr = ""
        arguments.forEach {
            argumentsStr += "/${it}"
        }
        return argumentsStr
    }

    companion object {
        inline fun <reified Params> construct(state: SavedStateHandle): Params {
            val constructor = Params::class.constructors.firstOrNull()
                ?: return EmptyScreenParams as Params
            val parameters = constructor.parameters.mapNotNull { parameter ->
                parameter.name?.let {
                    state[it]
                }
            }
            return constructor.call(*parameters.toTypedArray())
        }
    }
}