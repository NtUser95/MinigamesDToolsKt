package com.gmail.borlandlp.minigamesdtools.arena.scenario

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDeathLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerKilledLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerLeaveLocalEvent

class ScenarioListener(private val scenarioChainController: ScenarioChainController) : ArenaEventListener {
    fun onPlayerDeath(event: ArenaPlayerDeathLocalEvent) {}
    fun onPlayerKilled(event: ArenaPlayerKilledLocalEvent) {}
    fun onPlayerLeave(event: ArenaPlayerLeaveLocalEvent) {}
    fun onPlayerJoin(event: ArenaPlayerJoinLocalEvent) {}
}