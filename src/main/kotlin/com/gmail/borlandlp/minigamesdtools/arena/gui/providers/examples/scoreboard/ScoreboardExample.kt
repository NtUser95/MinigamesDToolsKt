package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.scoreboard

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider
import me.johnnykpl.scoreboardwrapper.ScoreboardWrapper
import org.bukkit.Bukkit

class ScoreboardExample : GUIProvider {
    private var scoreboardWrapper: ScoreboardWrapper? = null

    constructor(arena: ArenaBase?) : super(arena) {}
    constructor() {}

    override fun onInit() {
        scoreboardWrapper = ScoreboardWrapper("Title")
        scoreboardWrapper!!.addLine("example text")
        scoreboardWrapper!!.addBlankSpace()
        scoreboardWrapper!!.addLine("example text 2")
    }

    override fun beforeGameStarting() {}
    override fun gameEnded() {
        for (team in arena!!.teamController!!.teams) {
            for (player in team.players) {
                player.scoreboard = Bukkit.getScoreboardManager().newScoreboard
            }
        }
    }

    override fun update() {
        for (team in arena!!.teamController!!.teams) {
            for (player in team.players) {
                player.scoreboard = scoreboardWrapper!!.scoreboard
            }
        }
    }

    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
}