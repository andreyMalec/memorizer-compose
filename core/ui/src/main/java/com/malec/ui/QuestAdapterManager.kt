package com.malec.ui

import androidx.compose.runtime.Composable
import com.malec.domain.entity.OnQuestComplete
import com.malec.domain.entity.Quest
import java.util.LinkedList

class QuestAdapterManager(
    private val listener: OnQuestComplete
) {
    private val adapters = LinkedList<QuestAdapter>()

    fun init(vararg adapters: QuestAdapter) {
        this.adapters.addAll(adapters)
    }

    @Composable
    fun ItemContent(quest: Quest) {
        adapters.find { adapter ->
            adapter.questClass == quest::class
        }?.Bind(quest, listener)
    }
}