package com.malec.memorizer.mediator

import androidx.navigation.NamedNavArgument
import com.malec.memorizer.coordinator.base.Coordinator

interface Mediator<In : Coordinator, Out : Coordinator> {
    val root: String
    val arguments: List<NamedNavArgument>
}