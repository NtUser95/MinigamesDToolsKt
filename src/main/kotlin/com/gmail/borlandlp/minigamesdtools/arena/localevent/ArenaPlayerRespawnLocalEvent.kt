package com.gmail.borlandlp.minigamesdtools.arena.localevent

import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider
import org.bukkit.entity.Player

class ArenaPlayerRespawnLocalEvent(val teamProvider: TeamProvider, val player: Player) : ArenaEvent()