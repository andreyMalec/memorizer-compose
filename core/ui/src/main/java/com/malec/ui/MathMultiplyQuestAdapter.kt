package com.malec.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.malec.domain.entity.OnQuestComplete
import com.malec.domain.entity.Quest
import com.malec.domain.entity.quests.MathMultiplyQuest
import com.malec.ui.theme.Typography

class MathMultiplyQuestAdapter : QuestAdapter {
    override val questClass = MathMultiplyQuest::class

    @Composable
    override fun Bind(quest: Quest, listener: OnQuestComplete?) {
        quest as MathMultiplyQuest
        val focusManager = LocalFocusManager.current
        val completed = rememberSaveable { mutableStateOf(false) }
        val input = rememberSaveable { mutableStateOf("") }
        val text = "${quest.first} * ${quest.second}"
        Text(text = text, modifier = Modifier.padding(4.dp), fontSize = Typography.h2.fontSize)
        TextField(
            value = input.value,
            enabled = !completed.value,
            keyboardOptions = KeyboardTypeNumbers.copy(
                imeAction = ImeAction.Next
            ),
            onValueChange = {
                input.value = it
                val result = try {
                    input.value.toInt()
                } catch (_: NumberFormatException) {
                    null
                }
                result?.let {
                    if (quest.checkCompletion(result)) {
                        focusManager.moveFocus(FocusDirection.Next)
                        completed.value = true
                        listener?.onComplete(quest)
                    }
                }
            })
    }
}