package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d

import org.bukkit.ChatColor
import org.bukkit.Location

interface Marker {
    fun isOwner(`object`: Any): Boolean
    val color: ChatColor
    val location: Location
}