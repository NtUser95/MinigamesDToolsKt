package com.gmail.borlandlp.minigamesdtools.arena.commands

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import java.util.*

class DefaultWatcher : Listener, ArenaCommandWatcher {
    var whitelisted: MutableList<Array<String>> = mutableListOf()
    var blacklisted: MutableList<Array<String>> = mutableListOf()
    private var arena: ArenaBase? = null
    fun setArena(arena: ArenaBase?) {
        this.arena = arena
    }

    fun addBlacklisted(command: Array<String>) {
        blacklisted.add(command)
    }

    fun addWhitelisted(command: Array<String>) {
        whitelisted.add(command)
    }

    /**
     * Формат для правил: command param1 param2 *
     * Символ * означает, что любая дальнейшая последовательсть будет истинна.
     * Если символ стоит в самом начале, то любая команда с любыми параметрами будет истинна.
     * Если правило состоит из 3 параметров и не заканчивается на *, а команда из 4 идентичных параметров - результат будет ложью.
     * @param commandsList
     * @param command
     * @return
     */
    fun containsRuleSequence(
        commandsList: List<Array<String>>,
        command: Array<String>
    ): Boolean {
        for (listRule in commandsList) {
            var validEntries = 0
            for (i in listRule.indices) {
                if (i >= command.size) { // outbound
                    break
                } else if (listRule[i] == "*") {
                    return true
                } else if (listRule[i] != command[i]) {
                    break
                } else { // listRule[i].equals[command[i]]
                    if (++validEntries >= command.size && command.size == listRule.size) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun isAllowedCommand(command: Array<String>): Boolean {
        if (containsRuleSequence(blacklisted, command)) {
            if (!containsRuleSequence(whitelisted, command)) {
                return false
            }
        }
        return true
    }

    @EventHandler
    override fun onCommand(event: PlayerCommandPreprocessEvent) {
        val arena = instance!!.arenaAPI!!.getArenaOf(event.player) ?: return
        if (arena === this.arena) {
            val command = event.message.replace("/", "").split(" ").toTypedArray()
            if (!isAllowedCommand(command)) {
                event.player.sendMessage("{arena_rejected_msg}")
                event.isCancelled = true
            }
        }
    }

    override fun onInit() {
        Bukkit.getPluginManager().registerEvents(this, instance)
    }

    override fun beforeGameStarting() {}
    override fun gameEnded() {
        HandlerList.unregisterAll(this)
    }

    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}