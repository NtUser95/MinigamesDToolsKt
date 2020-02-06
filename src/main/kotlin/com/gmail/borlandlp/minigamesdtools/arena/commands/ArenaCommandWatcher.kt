package com.gmail.borlandlp.minigamesdtools.arena.commands

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import org.bukkit.event.player.PlayerCommandPreprocessEvent

interface ArenaCommandWatcher : ArenaPhaseComponent {
    fun onCommand(event: PlayerCommandPreprocessEvent)
}