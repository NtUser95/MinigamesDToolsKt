package com.gmail.borlandlp.minigamesdtools.arena

interface ArenaPhaseComponent {
    fun onInit()
    fun beforeGameStarting()
    fun gameEnded()
    fun update()
    fun beforeRoundStarting()
    fun onRoundEnd()
}