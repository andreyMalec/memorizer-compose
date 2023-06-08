package com.malec.domain

import com.malec.domain.entity.Quest
import kotlinx.coroutines.flow.Flow

interface QuestRepo {
    fun quests(): Flow<List<Quest>>

    fun next()
}