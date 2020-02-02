package com.gmail.borlandlp.minigamesdtools.conditions.examples

import com.gmail.borlandlp.minigamesdtools.conditions.AbstractCondition
import org.bukkit.entity.Player

class ExampleCondition : AbstractCondition() {
    override fun isValidPlayer(player: Player): Boolean {
        return true
    }

    override val errorId: String
        get() = "test_error_msg"
}