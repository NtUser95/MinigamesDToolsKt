package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider

class BossbarRadar : GUIProvider {
    constructor(arena: ArenaBase) : super(arena) {}
    constructor() {}

    override fun onInit() {}
    override fun beforeGameStarting() {}
    override fun gameEnded() {}
    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}