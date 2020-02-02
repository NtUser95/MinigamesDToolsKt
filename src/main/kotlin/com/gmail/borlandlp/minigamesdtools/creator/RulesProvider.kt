package com.gmail.borlandlp.minigamesdtools.creator

import java.util.*

class RulesProvider {
    private val rules: MutableList<String> = ArrayList()

    fun addReqiredField(key: String) {
        rules.add(key)
    }

    fun remove(key: String) {
        rules.remove(key)
    }

    fun isCorrectProvider(dataProvider: AbstractDataProvider): Boolean {
        for (rulesKey in rules) {
            if (!dataProvider.contains(rulesKey)) {
                return false
            }
        }
        return true
    }
}