package com.malec.main

import com.malec.domain.QuestRepo
import com.malec.domain.entity.OnQuestComplete
import com.malec.domain.entity.Quest
import com.malec.domain.entity.quests.MathMultiplyQuest
import com.malec.domain.entity.quests.MathSumQuest
import kotlinx.coroutines.flow.MutableStateFlow

class QuestRepoImpl : QuestRepo, OnQuestComplete {
    private var index = 0

    private var completed = 0

    private val quests = MutableStateFlow(questLine[index++])

    override fun quests() = quests

    override fun next() {
        if (index < questLine.size) {
            completed = 0
            quests.tryEmit(questLine[index++])
        }
    }

    private companion object {
        val questLine = listOf(
            (0..1).map { MathMultiplyQuest() },
            (0..3).map { MathSumQuest() },
            (0..3).map { MathMultiplyQuest() },
            (0..5).map { MathSumQuest() },
            emptyList(),
        )
    }

    override fun onComplete(quest: Quest) {
        if (++completed == questLine[index - 1].size)
            next()
    }

}