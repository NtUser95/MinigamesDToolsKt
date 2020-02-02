package com.gmail.borlandlp.minigamesdtools.activepoints.behaviors

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint

abstract class Behavior {
    lateinit var activePoint: ActivePoint
    lateinit var name: String
    var delay = 0
    var period = 0

    abstract fun tick()
}