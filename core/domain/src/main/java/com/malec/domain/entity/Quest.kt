package com.malec.domain.entity

interface Quest {
    fun checkCompletion(vararg requirements: Any?): Boolean
}