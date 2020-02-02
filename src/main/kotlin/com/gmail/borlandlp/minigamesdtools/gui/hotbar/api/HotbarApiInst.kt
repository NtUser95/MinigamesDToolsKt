package com.gmail.borlandlp.minigamesdtools.gui.hotbar.api

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.HotbarListener
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.HeldHotbar
import net.minecraft.server.v1_12_R1.PacketPlayOutExperience
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.util.*

class HotbarApiInst : HotbarAPI, APIComponent {
    private val players: MutableMap<Player, Hotbar> = Hashtable()
    private var hotbarListener: HotbarListener? = null
    private var task: BukkitTask? = null

    override fun onLoad() {
        hotbarListener = HotbarListener(this)
        Bukkit.getServer().pluginManager.registerEvents(hotbarListener, MinigamesDTools.instance)
        val task = this
        this.task = object : BukkitRunnable() {
            override fun run() {
                task.update()
            }
        }.runTaskTimer(MinigamesDTools.instance, 0, 5)
    }

    override fun onUnload() {
        HandlerList.unregisterAll(hotbarListener)
        task!!.cancel()
        for (player in all.keys) {
            try {
                unbindHotbar(player)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun update() {
        mutableListOf(all.keys).forEach { hotbarsSet ->
            hotbarsSet.forEach { player ->
                if (player.isDead == true) return
                all[player]!!.update()
                all[player]!!.draw()
            }
        }
    }

    private fun clearPlayerHotbar(p: Player?) {
        for (i in 0..8) {
            p!!.inventory.setItem(i, null)
        }
    }

    override fun bindHotbar(hotbar: Hotbar, player: Player) {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "Bind hotbar for Player[name:" + player.name + "]"
        )
        if (hotbar is HeldHotbar) {
            player.inventory.heldItemSlot = 8
        }
        players[player] = hotbar
        val packet = PacketPlayOutExperience(0.toFloat(), 0, 0)
        (player as CraftPlayer?)!!.handle.playerConnection.sendPacket(packet)
    }

    override fun unbindHotbar(player: Player) {
        if (!isBindedPlayer(player)) {
            return
        }
        Debug.print(
            Debug.LEVEL.NOTICE,
            "Unbind hotbar for Player[name:" + player.name + "]"
        )
        clearPlayerHotbar(player)
        players.remove(player)
        val packet =
            PacketPlayOutExperience(player.exp, player.totalExperience, player.level)
        (player as CraftPlayer?)!!.handle.playerConnection.sendPacket(packet)
    }

    override fun isBindedPlayer(player: Player): Boolean {
        return players.containsKey(player)
    }

    override fun getHotbar(player: Player): Hotbar {
        return players[player]!!
    }

    override val all: Map<Player, Hotbar>
        get() = HashMap(players)
}