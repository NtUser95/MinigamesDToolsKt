package com.gmail.borlandlp.minigamesdtools.arena

class GameRules {
    @JvmField
    var playerCanRegainHealth = false
    @JvmField
    var hungerDisable = false
    @JvmField
    var playerCanItemDrop = false
    @JvmField
    var playerCanItemPickup = false
    @JvmField
    var beforeFightDisableMoving = true
    @JvmField
    var minPlayersToStart = 2
    @JvmField
    var maxRounds = 2
    @JvmField
    var roundTime = 60
    @JvmField
    var beforeRoundStartingWaitDuration = 10
    @JvmField
    var beforeArenaTeleportWaitDuration = 10
}