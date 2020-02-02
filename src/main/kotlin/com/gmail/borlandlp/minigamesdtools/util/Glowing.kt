package com.gmail.borlandlp.minigamesdtools.util

import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.inventivetalent.glow.GlowAPI
import java.util.*

object Glowing {
    fun addGlowEffect(entity: Entity, colorName: String = "") {
        val glowColor = GlowAPI.Color.valueOf(colorName)
        val players: Collection<Player> = ArrayList(Bukkit.getServer().onlinePlayers)
        GlowAPI.setGlowing(entity, glowColor, players)
    }

    fun removeGlowEffect(entity: Entity) {
        val players: Collection<Player> = listOf(Bukkit.getServer().onlinePlayers) as Collection<Player>
        GlowAPI.setGlowing(entity, null, players)
    }

    @JvmStatic
    val availableColors: List<String>
        get() {
            return mutableListOf<String>().apply {
                for (color in GlowAPI.Color.values()) {
                    this.add(color.name)
                }
            }
        }
}