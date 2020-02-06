package com.gmail.borlandlp.minigamesdtools.arena.scenario

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent

interface Scenario : ArenaPhaseComponent {
    val isDone: Boolean
    fun roundMustBeEnded(): Boolean
    fun gameMustBeEnded(): Boolean
}