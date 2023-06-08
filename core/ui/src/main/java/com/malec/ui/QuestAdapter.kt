package com.malec.ui

import androidx.compose.runtime.Composable
import com.malec.domain.entity.OnQuestComplete
import com.malec.domain.entity.Quest
import kotlin.reflect.KClass

interface QuestAdapter {
    val questClass: KClass<*>

    @Composable
    fun Bind(quest: Quest, listener: OnQuestComplete?)
}