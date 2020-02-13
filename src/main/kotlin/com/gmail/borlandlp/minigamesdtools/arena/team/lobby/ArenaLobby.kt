package com.gmail.borlandlp.minigamesdtools.arena.team.lobby

import com.gmail.borlandlp.minigamesdtools.DefaultCreators
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar
import org.bukkit.Location
import org.bukkit.entity.Player

abstract class ArenaLobby {
    var teamProvider: TeamProvider? = null
    var spawnPoint: Location? = null
    var isEnabled = false
    var isHotbarEnabled = false
    var hotbarId: String? = null

    fun getHotbarFor(player: Player): Hotbar? {
        var hotbar: Hotbar? = null
        try {
            hotbar = instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR.pseudoName)!!.create(
                hotbarId!!,
                DataProvider().apply {
                    this["player"] = player
                }) as Hotbar
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return hotbar
    }

    /*
    * TODO: Перевести на аглийский.
    * TeamProvider может запросить принудительное отключение игрока от лобби, если ему так потребуется.
    * В остальных случаях, лобби само решает, как ему распоряжаться с этим игроком.
    * */
    abstract fun forceReleasePlayer(p: Player)

    abstract fun addPlayer(player: Player)
}