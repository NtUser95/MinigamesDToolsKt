package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import java.util.*

class BossbarExample : GUIProvider {
    var message: String? = null
    var percentage = 0.0f
    private var bossBar: BossBar? = null

    constructor(arena: ArenaBase?) : super(arena) {}
    constructor() {}

    override fun gameEnded() {
        if (bossBar == null) {
            return
        }
        for (team in arena!!.teamController!!.teams) {
            for (player in team.players) {
                bossBar!!.removePlayer(player)
            }
        }
    }

    override fun onInit() {}
    override fun beforeGameStarting() {
        val color = BarColor.values()[Random().nextInt(BarColor.values().size - 1)]
        val style = BarStyle.values()[Random().nextInt(BarStyle.values().size - 1)]
        bossBar = Bukkit.createBossBar("BossBarExample", color, style)
        bossBar!!.progress = Random().nextDouble()
    }

    override fun update() {
        bossBar!!.color = BarColor.values()[Random().nextInt(BarColor.values().size - 1)]
        bossBar!!.style = BarStyle.values()[Random().nextInt(BarStyle.values().size - 1)]
        for (team in arena!!.teamController!!.teams) {
            for (player in team.players) {
                if (!bossBar!!.players.contains(player)) {
                    bossBar!!.addPlayer(player)
                    bossBar!!.isVisible = true
                }
            }
        }
    }

    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}

    fun removeMessage() {
        message = null
    }
}