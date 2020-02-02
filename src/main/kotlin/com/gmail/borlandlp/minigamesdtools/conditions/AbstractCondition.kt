package com.gmail.borlandlp.minigamesdtools.conditions

import org.bukkit.entity.Player

abstract class AbstractCondition {
    abstract fun isValidPlayer(player: Player): Boolean
    abstract val errorId: String
}