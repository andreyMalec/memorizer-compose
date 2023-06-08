package com.malec.domain.entity.quests

import com.malec.domain.entity.Quest
import com.malec.domain.entity.RequirementType

class MathSumQuest : Quest {
    val first = (0..100).random()
    val second = (0..100).random()

    @RequirementType(Int::class)
    override fun checkCompletion(vararg requirements: Any?) = first + second == requirements.first()
}
