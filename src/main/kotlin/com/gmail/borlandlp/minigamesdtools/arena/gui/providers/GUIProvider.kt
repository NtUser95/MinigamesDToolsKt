package com.gmail.borlandlp.minigamesdtools.arena.gui.providers

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent

abstract class GUIProvider : ArenaPhaseComponent {
    var arena: ArenaBase? = null

    constructor(arenaBase: ArenaBase?) {
        arena = arenaBase
    }

    constructor() {}

}