package com.malec.domain.entity.quests

import com.malec.domain.entity.Quest
import com.malec.domain.entity.RequirementType

class MathMultiplyQuest : Quest {
    val first = (0..10).random()
    val second = (0..10).random()

    @RequirementType(Int::class)
    override fun checkCompletion(vararg requirements: Any?) = first * second == requirements.first()
}
