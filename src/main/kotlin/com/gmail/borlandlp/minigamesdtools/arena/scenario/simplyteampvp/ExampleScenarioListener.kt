package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDeathLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerKilledLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.scenario.Scenario

class ExampleScenarioListener(private val scenario: Scenario) : ArenaEventListener {
    @ArenaEventHandler
    fun playerDied(event: ArenaPlayerDeathLocalEvent) {}

    @ArenaEventHandler
    fun playerKilled(event: ArenaPlayerKilledLocalEvent) {}
}