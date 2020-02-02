package com.gmail.borlandlp.minigamesdtools.conditions

import org.bukkit.entity.Player
import java.util.*

class ConditionsChain(private val conditions: List<AbstractCondition>) {

    fun check(player: Player): PlayerCheckResult {
        val errIds: MutableList<String> = mutableListOf()
        conditions.forEach { abstractCondition ->
            if (!abstractCondition.isValidPlayer(player)) {
                errIds.add(abstractCondition.errorId)
            }
        }
        return PlayerCheckResult(
            if (errIds.size > 0) PlayerCheckResult.CheckResult.DENIED else PlayerCheckResult.CheckResult.ALLOWED,
            errIds
        )
    }

}